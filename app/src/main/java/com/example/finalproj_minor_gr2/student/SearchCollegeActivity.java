package com.example.finalproj_minor_gr2.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproj_minor_gr2.Adapters.CustomAdapterRcvSearchTeacher;
import com.example.finalproj_minor_gr2.R;
import com.example.finalproj_minor_gr2.model_classes.ModelClassDemoSearchTeacher;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.libizo.CustomEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class SearchCollegeActivity extends AppCompatActivity {
    CustomEditText customEditText;
    String unameChecker, getUnameCheckerNext;
    Button searchCollegeBtn;
    MaterialSpinner spinnerLevelTeacherSearchCollege;
    String[] levelTeacherSearchCollege = {"Select College Type", "Engg", "Hons", "Hm"};
    String seletedLevel = "";
    boolean isValidPin = false, isValidLevel = false;
    ArrayList<ModelClassDemoSearchTeacher> activityList = new ArrayList<>();
    RecyclerView rcv;
    CustomAdapterRcvSearchTeacher rcvAdaptor;
    ModelClassDemoSearchTeacher modelClassDemo;
    PrettyDialog prettyDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_college);
        ViewBinder();
        spinnerLevelTeacherSearchCollege.setItems(levelTeacherSearchCollege);
        spinnerLevelTeacherSearchCollege.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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
           ParseQuery<ParseUser> query=ParseUser.getQuery();
           query.whereEqualTo("username",menulist.get(position).getActivityName());
           query.findInBackground(new FindCallback<ParseUser>() {
               @Override
               public void done(List<ParseUser> objects, ParseException e) {
                   for (ParseUser user:objects){
                       prettyDialog = new PrettyDialog(SearchCollegeActivity.this);
                       prettyDialog.setTitle("Info")
                               .setMessage("Name :" + (user.getUsername()) + "\n" + "Website: " +
                                       user.get("website") + "\n" + "Description: " +
                                       user.get("description") + "\n" + "Phone: " +
                                       user.get("Phone") + "\n" + "Location: " +
                                       user.get("actuallocation")).setIcon(R.drawable.full_name_draw)
                               .setIconTint(R.color.colorFlower)
                               .addButton(
                                       "Save",                    // button text
                                       R.color.pdlg_color_white,        // button text color
                                       R.color.pdlg_color_green,        // button background color
                                       new PrettyDialogCallback() {        // button OnClick listener
                                           @Override
                                           public void onClick() {
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
                               )
                               .show();

                   }
               }
           });


            }
        });


        searchCollegeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PinValidator(customEditText.getText().toString());
                LvelValidator(seletedLevel);
                if (isValidLevel && isValidPin) {
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("Regtype", "College");
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

                }

            }
        });


    }
    private void LvelValidator(String seletedLevel) {
        if (seletedLevel.equals("")) {
            FancyToast.makeText(SearchCollegeActivity.this, "Please select college's level", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidLevel = false;
        } else {
            isValidLevel = true;
        }
    }

    private void PinValidator(String pin) {
        if (pin.equals("")) {
            FancyToast.makeText(SearchCollegeActivity.this, "Please Enter a pincode", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidPin = false;
        } else {
            isValidPin = true;

        }

    }

    private void ViewBinder() {
        customEditText = findViewById(R.id.etPincodeSearchCollege);
        searchCollegeBtn = findViewById(R.id.searchCollegeBtn);
        spinnerLevelTeacherSearchCollege = findViewById(R.id.spinnerLevelTeacherSearchCollege);
        rcv = findViewById(R.id.rcvFragmentSearchCollege);
        rcvAdaptor = new CustomAdapterRcvSearchTeacher(SearchCollegeActivity.this, activityList);


    }
}
