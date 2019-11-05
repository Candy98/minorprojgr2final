package com.example.finalproj_minor_gr2.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproj_minor_gr2.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MessageActivity extends AppCompatActivity {
    EditText etSendMessageStud;
    Button btnSendMessagetud;
    Intent intent;
    String fromname, type, toName, phno;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ViewBinder();
        fromname = getIntent().getStringExtra("fromname");
        type = getIntent().getStringExtra("type");
        toName = getIntent().getStringExtra("toName");
        phno = getIntent().getStringExtra("phone");
        btnSendMessagetud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();

            }
        });



    }

    private void SendMessage() {
        switch (type) {
            case "Teacher":
                ParseSendMessage("TeachersMessageStoreFromStudents");
                break;
        }
    }

    private void ParseSendMessage(String className) {
        ParseObject parseObject = new ParseObject(className);
        ParseQuery query = new ParseQuery(className);
        query.whereEqualTo("toname", toName);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        parseObject.put("fromname", fromname);
                        parseObject.put("toname", toName);
                        parseObject.put("messages", etSendMessageStud.getText() + "");
                        parseObject.put("phno", phno);
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(MessageActivity.this, "Message sent to " + toName, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                                } else {
                                    FancyToast.makeText(MessageActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                                }
                            }
                        });
                    } else {
                        parseObject.add("messages", etSendMessageStud.getText() + "");
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(MessageActivity.this, "Message sent to " + toName, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                                } else {
                                    FancyToast.makeText(MessageActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void done(Object o, Throwable throwable) {

            }
        });


    }

    private void ViewBinder() {
        etSendMessageStud = findViewById(R.id.etSendMessageStud);
        btnSendMessagetud = findViewById(R.id.btnSendMessageStud);
    }
}
