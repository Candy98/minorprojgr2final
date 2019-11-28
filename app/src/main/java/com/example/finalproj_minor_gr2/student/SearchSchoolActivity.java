package com.example.finalproj_minor_gr2.student;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproj_minor_gr2.Adapters.CustomAdapterRcvSearchTeacher;
import com.example.finalproj_minor_gr2.R;
import com.example.finalproj_minor_gr2.model_classes.ModelClassDemoSearchTeacher;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.libizo.CustomEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class SearchSchoolActivity extends AppCompatActivity {
    CustomEditText customEditText;
    PrettyDialog prettyDialog;
    String unameChecker, getUnameCheckerNext,name;
    Button searchSchoolBtn;
    MaterialSpinner spinnerLevelTeacherSearchSchool;
    String[] levelTeacherSearchSchool = {"Select level", "Primary School", "High School", "Higher Secondary"};
    String seletedLevel = "";
    boolean isValidPin = false, isValidLevel = false;
    ArrayList<ModelClassDemoSearchTeacher> activityList = new ArrayList<>();
    RecyclerView rcv;
    CustomAdapterRcvSearchTeacher rcvAdaptor;
    ModelClassDemoSearchTeacher modelClassDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);
        ViewBinder();
        modelClassDemo=new ModelClassDemoSearchTeacher();
        modelClassDemo.setActivityName("Hi");
        activityList.add(modelClassDemo);
        spinnerLevelTeacherSearchSchool.setItems(levelTeacherSearchSchool);
        spinnerLevelTeacherSearchSchool.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position != 0) {
                    seletedLevel = item.toString();
                    Toast.makeText(SearchSchoolActivity.this, seletedLevel, Toast.LENGTH_SHORT).show();

                } else {
                    seletedLevel = "";
                }
            }
        });


        searchSchoolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                PinValidator(customEditText.getText().toString());
                LvelValidator(seletedLevel);
                if (isValidLevel && isValidPin) {
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("Regtype", "School");
                    query.whereEqualTo("pincode", customEditText.getText().toString());
                    query.whereEqualTo("level", seletedLevel);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0) {
                                    for (ParseUser user : objects) {
                                        modelClassDemo = new ModelClassDemoSearchTeacher();

                                        modelClassDemo.setActivityName(user.getUsername());
                                        modelClassDemo.setActivityLocation(user.get("actuallocation").toString());
                                        activityList.add(modelClassDemo);


                                    }
                                    rcv.setAdapter(rcvAdaptor);

                                }
                            }
                        }
                    });
                    rcvAdaptor.setOnItemClickListener(new CustomAdapterRcvSearchTeacher.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position, ArrayList<ModelClassDemoSearchTeacher> menulist) {
                            ParseFetchData(menulist.get(position).getActivityName());
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
                            prettyDialog = new PrettyDialog(SearchSchoolActivity.this);
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
                            ).addButton(
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
                                            Intent intent=new Intent(SearchSchoolActivity.this,MessageActivity.class);
                                            intent.putExtra("fromname",ParseUser.getCurrentUser().getUsername());
                                            intent.putExtra("phone",user.get("Phone").toString());
                                            intent.putExtra("type",user.get("Regtype").toString());
                                            intent.putExtra("toName",user.getUsername());
                                            startActivity(intent);

                                        }
                                    }
                            )
                                    .show();


                        }

                    }
                }

            }
        });}

    private void LvelValidator(String seletedLevel) {
        if (seletedLevel.equals("")) {
            FancyToast.makeText(SearchSchoolActivity.this, "Please select school's level", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidLevel = false;
        } else {
            isValidLevel = true;
        }
    }

    private void PinValidator(String pin) {
        if (pin.equals("")) {
            FancyToast.makeText(SearchSchoolActivity.this, "Please Enter a pincode", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidPin = false;
        } else {
            isValidPin = true;

        }

    }

    private void ViewBinder() {
        customEditText = findViewById(R.id.etPincodeSearchSchool);
        searchSchoolBtn = findViewById(R.id.searchSchoolBtn);
        spinnerLevelTeacherSearchSchool = findViewById(R.id.spinnerLevelTeacherSearchSchool);
        rcv = findViewById(R.id.rcvFragmentSearchSchool);
        rcvAdaptor = new CustomAdapterRcvSearchTeacher(SearchSchoolActivity.this, activityList);


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
                    FancyToast.makeText(SearchSchoolActivity.this, "Unfollowed" + user.getUsername(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
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
                                    FancyToast.makeText(SearchSchoolActivity.this, "Followed " + user.getUsername() + user.getUsername(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                                }
                            }
                        });
                    } else {
                        FancyToast.makeText(SearchSchoolActivity.this, "Already followed", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();

                    }

                }
            });
        } catch (Exception e) {

        }

    }


}
