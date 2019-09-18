package com.example.finalproj_minor_gr2.teacher;

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

public class TeacherMainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsernameTeacher, etCourseofferedTeacher, etLocationTeacher, etDescTeacher, etQualTeacher;
    Button updateTeachersInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        BindViews();
        final ParseUser parseUser = ParseUser.getCurrentUser();
        updateTeachersInfo.setOnClickListener(this);
        etUsernameTeacher.setText(parseUser.get("username") + "");

        etLocationTeacher.setText(parseUser.get("actuallocation") + "");
        etDescTeacher.setText(parseUser.get("description") + "");
        etCourseofferedTeacher.setText(parseUser.get("courseoffered") + "");
        etQualTeacher.setText(parseUser.get("qualification") + "");

        updateTeachersInfo.setOnClickListener(this);


    }


    private void BindViews() {

        etUsernameTeacher = findViewById(R.id.etUsernameTeacher);
        etCourseofferedTeacher = findViewById(R.id.etCourseofferedTeacher);
        etLocationTeacher = findViewById(R.id.etLocationTeacher);
        etDescTeacher = findViewById(R.id.etDescTeacher);
        updateTeachersInfo = findViewById(R.id.updateTeachersInfo);

        etQualTeacher = findViewById(R.id.etQualTeacher);


    }

    @Override
    public void onClick(View view) {
        final ParseUser parseUser = ParseUser.getCurrentUser();


        parseUser.put("username", etUsernameTeacher.getText().toString());
        parseUser.put("courseoffered", etCourseofferedTeacher.getText().toString());
        parseUser.put("actuallocation", etLocationTeacher.getText().toString());
        parseUser.put("description", etDescTeacher.getText().toString());
        parseUser.put("qualification", etQualTeacher.getText().toString());


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


