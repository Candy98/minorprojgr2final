package com.example.finalproj_minor_gr2.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproj_minor_gr2.LoginACtivity;
import com.example.finalproj_minor_gr2.R;
import com.parse.ParseUser;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class StudentProfileActivity extends AppCompatActivity {
    LinearLayout stud_prof, stud_teacher, stud_school, stud_college, stud_logout, stud_saved;
    PrettyDialog prettyDialog;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        BindViews();
        Onclick();

    }

    private void Onclick() {
        stud_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentProfileActivity.this, FollowedUsersActivity.class));
            }
        });
        stud_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfileActivity.this, StudSearchTeacherActivity.class));
            }
        });
        stud_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                startActivity(new Intent(StudentProfileActivity.this, LoginACtivity.class));
                StudentProfileActivity.this.finish();
            }
        });
        stud_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfileActivity.this, SearchCollegeActivity.class));
            }
        });
        stud_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfileActivity.this, SearchSchoolActivity.class));
            }
        });
        stud_prof.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ParseUser user = ParseUser.getCurrentUser();
                prettyDialog = new PrettyDialog(StudentProfileActivity.this);
                prettyDialog.setTitle("Info")
                        .setMessage("Name :" + (user.getUsername()) + "\n" + "Website: " +
                                user.get("website") + "\n" + "Description: " +
                                user.get("description") + "\n" + "Phone: " +
                                user.get("Phone") + "\n" + "Location: " +
                                user.get("actuallocation")


                        ).setIcon(R.drawable.full_name_draw)
                        .setIconTint(R.color.colorFlower)
                        .addButton(
                                "Edit",                    // button text
                                R.color.pdlg_color_white,        // button text color
                                R.color.pdlg_color_green,        // button background color
                                new PrettyDialogCallback() {        // button OnClick listener
                                    @Override
                                    public void onClick() {
                                        TransEditStudProf();
                                    }
                                }
                        )

// Cancel button
                        .addButton(
                                "Cancel",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_red,
                                new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        prettyDialog.dismiss();
                                        // Dismiss
                                    }
                                }
                        ).addButton(
                        "Message",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();

                                // Dismiss
                            }
                        }
                )
                        .show();

            }
        });
    }

    private void TransEditStudProf() {
        prettyDialog.dismiss();
        startActivity(new Intent(StudentProfileActivity.this, StudentProfEditActivity.class));

    }

    private void BindViews() {
        stud_prof = findViewById(R.id.stud_prof);
        stud_teacher = findViewById(R.id.stud_teacher);
        stud_school = findViewById(R.id.stud_school);
        stud_college = findViewById(R.id.stud_college);
        stud_logout = findViewById(R.id.stud_logout);
        stud_saved = findViewById(R.id.stud_saved);
    }


}
