package com.example.finalproj_minor_gr2.teacher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproj_minor_gr2.Adapters.CustomAdapterRcvSearchTeacher;
import com.example.finalproj_minor_gr2.R;
import com.example.finalproj_minor_gr2.model_classes.ModelClassDemoSearchTeacher;
import com.example.finalproj_minor_gr2.student.MessageActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.libizo.CustomEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class TeacherSearchStudentActivity extends AppCompatActivity {

    CustomEditText customEditText;
    String unameChecker, getUnameCheckerNext;
    BottomSheetBehavior behavior;
    CoordinatorLayout bottomSheetCoordinatorLayout;
    LinearLayout bottomSheetTeachers;
    Button buttonBottomSheetSaveTeacher, buttonBottomSheetCancelTeacher;
    PrettyDialog prettyDialog;
    int[] bgrcv = {R.drawable.bgcardviewcolor, R.drawable.bgcardviewcolor1, R.drawable.bgcardviewcolor2,
            R.drawable.bgcardviewcolor3, R.drawable.bgcardviewcolor4, R.drawable.bgcardviewcolor5,
            R.drawable.bgcardviewcolor6
    };
    Random random;
    int pos = 0;


    TextView tvDescTeachers, tvSubOfferedTeacher, tvPhnNoTeacher, tvEmailTeacher, tvQualificationTeacher, tvWebsiteTeacher;
    Button searchTeacherBtn;
    MaterialSpinner spinnerLevelTeacherSearchTeacher;
    String[] levelTeacherSearchTeacher = {"Select level", "Primary School", "High School", "Higher Secondary School", "Btech", "BCA"};
    String seletedLevel = "";
    boolean isValidPin = false, isValidLevel = false, isNotSaved = false;
    ArrayList<ModelClassDemoSearchTeacher> activityList = new ArrayList<>();
    RecyclerView rcv;
    CustomAdapterRcvSearchTeacher rcvAdaptor;
    ModelClassDemoSearchTeacher modelClassDemo;

    String name, type;
    boolean canFollow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_search_student);
        ViewBinder();


        spinnerLevelTeacherSearchTeacher.setItems(levelTeacherSearchTeacher);
        spinnerLevelTeacherSearchTeacher.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position != 0) {
                    seletedLevel = item.toString();

                } else {
                    seletedLevel = "";
                }
            }
        });
        rcvAdaptor.setOnItemClickListener(new CustomAdapterRcvSearchTeacher.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, ArrayList<ModelClassDemoSearchTeacher> menulist) {
                ParseFetchData(menulist.get(position).getActivityName());
            }
        });


      searchTeacherBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              PinValidator(customEditText.getText().toString());
              Log.i("Clicked",customEditText.getText().toString());
              LvelValidator(seletedLevel);
              if (isValidLevel && isValidPin) {
                  ParseQuery<ParseUser> query = ParseUser.getQuery();
                  query.whereEqualTo("Regtype", "Student");
                  Log.i("Clicked",seletedLevel);
                  query.whereEqualTo("pincode", customEditText.getText().toString());
                  query.whereEqualTo("level", seletedLevel);
                  query.findInBackground(new FindCallback<ParseUser>() {
                      @Override
                      public void done(List<ParseUser> objects, ParseException e) {
                          random = new Random();
                          if (e == null) {
                              if (objects.size() > 0) {
                                  for (ParseUser user : objects) {
                                      pos = random.nextInt(6);
                                      modelClassDemo = new ModelClassDemoSearchTeacher();
                                      modelClassDemo.setActivityName(user.getUsername());
                                      modelClassDemo.setActivityLocation(user.get("actuallocation").toString());
                                      modelClassDemo.setResource(bgrcv[pos]);
                                      activityList.add(modelClassDemo);


                                  }
                                  rcv.setAdapter(rcvAdaptor);


                              }
                          }
                      }
                  });

              }
          }
      });
    }

    private void ParseFetchData(final String activityName) {
        ParseQuery<ParseUser> userParseQuery = ParseUser.getQuery();
        name = activityName;

        userParseQuery.whereEqualTo("username", activityName);
        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser user : objects) {
                            prettyDialog = new PrettyDialog(TeacherSearchStudentActivity.this);
                            prettyDialog.setTitle("Info")
                                    .setMessage("Name :" + (user.getUsername()) + "\n" + "Website: " +
                                            user.get("website") + "\n" + "Description: " +
                                            user.get("description") + "\n" + "Phone: " +
                                            user.get("Phone") + "\n" + "Location: " +
                                            user.get("actuallocation")).setIcon(R.drawable.full_name_draw)
                                    .setIconTint(R.color.colorFlower)
                                    .addButton(
                                            "Follow",                    // button text
                                            R.color.pdlg_color_white,        // button text color
                                            R.color.pdlg_color_green,        // button background color
                                            new PrettyDialogCallback() {        // button OnClick listener
                                                @Override
                                                public void onClick() {
                                                    FollowUser(user);
                                                }
                                            }
                                    ).addButton(
                                    "Unfollow",
                                    R.color.pdlg_color_white,
                                    R.color.colorGrapeFruitDark,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {

                                            Unfollow(user);
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
                                    R.color.colorGrassDark,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            prettyDialog.dismiss();
                                            Intent intent = new Intent(TeacherSearchStudentActivity.this, MessageActivity.class);
                                            intent.putExtra("fromname", ParseUser.getCurrentUser().getUsername());
                                            intent.putExtra("phone", user.get("Phone").toString());
                                            intent.putExtra("type", user.get("Regtype").toString());
                                            intent.putExtra("toName", user.getUsername());
                                            startActivity(intent);

                                        }
                                    }
                            )  .addButton(
                                    "Call",
                                    R.color.pdlg_color_white,

                                    R.color.pdlg_color_red,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            // Dismiss
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", user.get("Phone").toString(), null));
                                            startActivity(intent);

                                        }
                                    }
                            )
                                    .show();


                        }

                    }
                }

            }
        });
    }

    private void Unfollow(ParseUser user) {
        ParseUser.getCurrentUser().getList("following").remove(user.getUsername());
        List currentFollowingUsers = ParseUser.getCurrentUser().getList("following");
        ParseUser.getCurrentUser().remove("following");
        ParseUser.getCurrentUser().put("following", currentFollowingUsers);
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FancyToast.makeText(TeacherSearchStudentActivity.this, "Unfollowed" + user.getUsername(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    prettyDialog.dismiss();
                }
            }
        });
    }

    private void FollowUser(ParseUser user) {

        try {


            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("following", user.getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null && objects.size() == 0) {
                        ParseUser.getCurrentUser().add("following", user.getUsername());
                        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(TeacherSearchStudentActivity.this, "Followed " + user.getUsername() + user.getUsername(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                                }
                            }
                        });
                    } else {
                        FancyToast.makeText(TeacherSearchStudentActivity.this, "Already followed", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();

                    }

                }
            });
        } catch (Exception e) {

        }

    }

    private void SaveTeacher() {
        ParseObject parseObject = new ParseObject("SavedData");
        ParseUser user = new ParseUser();
        parseObject.put("username", user.getUsername());
    }


    private void LvelValidator(String seletedLevel) {
        if (seletedLevel.equals("")) {
            FancyToast.makeText(TeacherSearchStudentActivity.this, "Please select teacher's level", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidLevel = false;
        } else {
            isValidLevel = true;
        }
    }

    private void PinValidator(String pin) {
        if (pin.equals("")) {
            FancyToast.makeText(TeacherSearchStudentActivity.this, "Please Enter a pincode", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidPin = false;
        } else {
            isValidPin = true;

        }

    }

    private void ViewBinder() {
        customEditText = findViewById(R.id.etPincodeSearchStudentTeacher);
        searchTeacherBtn = findViewById(R.id.searchStudentTeacherBtn);
        spinnerLevelTeacherSearchTeacher = findViewById(R.id.spinnerLevelStudSearchTeacherTeacher);
        rcv = findViewById(R.id.rcvFragmentSearchStudentTeachers);
        rcvAdaptor = new CustomAdapterRcvSearchTeacher(TeacherSearchStudentActivity.this, activityList);


    }
}
