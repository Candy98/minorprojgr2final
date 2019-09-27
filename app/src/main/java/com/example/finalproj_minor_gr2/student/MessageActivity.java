package com.example.finalproj_minor_gr2.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproj_minor_gr2.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MessageActivity extends AppCompatActivity {
    EditText etSendMessageStud;
    Button btnSendMessagetud;
    Intent intent;
    String fromname;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ViewBinder();
        fromname=getIntent().getStringExtra("fromname");

        progressDialog=new ProgressDialog(this);
        btnSendMessagetud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Sending message");
                progressDialog.show();
                ParseObject parseObject = new ParseObject("Messages");
                parseObject.put("from", ParseUser.getCurrentUser().getUsername());
                parseObject.put("to",fromname);
                parseObject.put("message", etSendMessageStud.getText() + "");
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            FancyToast.makeText(MessageActivity.this,"Sent",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                            progressDialog.dismiss();

                        }
                        else {
                            FancyToast.makeText(MessageActivity.this,e.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                            progressDialog.dismiss();


                        }
                    }
                });
            }
        });

    }

    private void ViewBinder() {
        etSendMessageStud = findViewById(R.id.etSendMessageStud);
        btnSendMessagetud = findViewById(R.id.btnSendMessageStud);
    }
}
