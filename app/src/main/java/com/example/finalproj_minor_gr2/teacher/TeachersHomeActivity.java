package com.example.finalproj_minor_gr2.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproj_minor_gr2.R;
import com.example.finalproj_minor_gr2.student.MessageActivity;
import com.parse.ParseUser;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class TeachersHomeActivity extends AppCompatActivity {
    LinearLayout teacher_prof, teacher_stud, teacher_followed, teacher_queries, teacher_logout;
    PrettyDialog prettyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_home);
        BindViews();
        OnClicks();
    }

    private void OnClicks() {
        teacher_followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeachersHomeActivity.this, FollowedStudentActivity.class));
            }
        });
        teacher_queries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeachersHomeActivity.this, MessageActivity.class));
            }
        });
        teacher_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeachersHomeActivity.this, TeacherSearchStudentActivity.class));


            }
        });
        teacher_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = ParseUser.getCurrentUser();
                prettyDialog = new PrettyDialog(TeachersHomeActivity.this);
                prettyDialog.setMessage("Name :" + (user.getUsername()) + "\n" + "Website: " +
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
                                        TransEditTeacherProf();
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

    private void TransEditTeacherProf() {
        startActivity(new Intent(TeachersHomeActivity.this, TeacherEditActivity.class));
    }

    private void BindViews() {
        teacher_prof = findViewById(R.id.teacher_prof);
        teacher_stud = findViewById(R.id.teacher_stud);
        teacher_followed = findViewById(R.id.teacher_followed);
        teacher_queries = findViewById(R.id.teacher_queries);
        teacher_logout = findViewById(R.id.teacher_logout);
    }
}
