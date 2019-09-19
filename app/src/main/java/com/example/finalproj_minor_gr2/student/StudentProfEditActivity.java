package com.example.finalproj_minor_gr2.student;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproj_minor_gr2.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class StudentProfEditActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsernameStud, etCourseStud, etLocationStud, etDescStud, etQualStud;
    Button updateStudentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_prof_edit);
        BindViews();
        final ParseUser parseUser = ParseUser.getCurrentUser();
        updateStudentInfo.setOnClickListener(this);
        etUsernameStud.setText(parseUser.get("username") + "");

        etLocationStud.setText(parseUser.get("actuallocation") + "");
        etDescStud.setText(parseUser.get("description") + "");
        etCourseStud.setText(parseUser.get("courseoffered") + "");
        etQualStud.setText(parseUser.get("qualification") + "");

        updateStudentInfo.setOnClickListener(this);

    }

    private void BindViews() {

        etUsernameStud = findViewById(R.id.etUsernameStud);
        etCourseStud = findViewById(R.id.etCourseStud);
        etLocationStud = findViewById(R.id.etLocationStud);
        etDescStud = findViewById(R.id.etDescStud);
        updateStudentInfo = findViewById(R.id.updateStudentInfo);

        etQualStud = findViewById(R.id.etQualStud);


    }

    @Override
    public void onClick(View v) {
        final ParseUser parseUser = ParseUser.getCurrentUser();


        parseUser.put("username", etUsernameStud.getText().toString());
        parseUser.put("courseoffered", etCourseStud.getText().toString());
        parseUser.put("actuallocation", etLocationStud.getText().toString());
        parseUser.put("description", etDescStud.getText().toString());
        parseUser.put("qualification", etQualStud.getText().toString());


        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FancyToast.makeText(getApplicationContext(), "Data Updated ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                } else {
                    FancyToast.makeText(getApplicationContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                }
            }
        });

    }
}

