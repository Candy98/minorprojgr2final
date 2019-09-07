package com.example.finalproj_minor_gr2.teacher;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproj_minor_gr2.R;
import com.parse.ParseUser;

public class TeacherMainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsernameTeacher, etCourseofferedTeacher, etLocationTeacher, etDescTeacher;
    Button updateTeachersInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        BindViews();
        final ParseUser parseUser=ParseUser.getCurrentUser();
        updateTeachersInfo.setOnClickListener(this);
        if (parseUser.get("username") == null) {
            etUsernameTeacher.setText("");
        } else {
            etUsernameTeacher.setText(parseUser.get("username") + "");

        }
        if (parseUser.get("courseoffered") == null) {
            etCourseofferedTeacher.setText("");

        } else {
            etCourseofferedTeacher.setText(parseUser.get("courseoffered") + "");

        }
        if (parseUser.get("actuallocation") == null) {
            etLocationTeacher.setText("");

        } else {
            etLocationTeacher.setText(parseUser.get("actuallocation") + "");

        }
        if (parseUser.get("Hobby") == null) {
            etHobby.setText("");

        } else {
            etHobby.setText(parseUser.get("Hobby") + "");

        }
        if (parseUser.get("Favouritesport") == null) {
            etFavSport.setText("");

        } else {
            etFavSport.setText(parseUser.get("Favouritesport") + "");

        }


    }

    private void BindViews() {
        etUsernameTeacher = findViewById(R.id.etUsernameTeacher);
        etCourseofferedTeacher = findViewById(R.id.etCourseofferedTeacher);
        etLocationTeacher = findViewById(R.id.etLocationTeacher);
        etDescTeacher = findViewById(R.id.etDescTeacher);
        updateTeachersInfo = findViewById(R.id.updateTeachersInfo);

    }

    @Override
    public void onClick(View view) {


    }
}
