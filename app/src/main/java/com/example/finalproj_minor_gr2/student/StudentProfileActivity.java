package com.example.finalproj_minor_gr2.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.finalproj_minor_gr2.R;
import com.google.android.material.tabs.TabLayout;

public class StudentProfileActivity extends AppCompatActivity {
LinearLayout stud_prof;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        BindViews();
        stud_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentProfileActivity.this,StudSearchTeacherActivity.class));
            }
        });
    }

    private void BindViews() {
        stud_prof=findViewById(R.id.stud_prof);
    }



}
