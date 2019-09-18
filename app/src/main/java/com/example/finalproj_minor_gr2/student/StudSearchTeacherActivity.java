package com.example.finalproj_minor_gr2.student;

import android.os.Bundle;
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

public class StudSearchTeacherActivity extends AppCompatActivity {
    CustomEditText customEditText;
    String unameChecker, getUnameCheckerNext;
    BottomSheetBehavior behavior;
    CoordinatorLayout bottomSheetCoordinatorLayout;
    LinearLayout bottomSheetTeachers;
    Button buttonBottomSheetSaveTeacher, buttonBottomSheetCancelTeacher;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_search_teacher);
        ViewBinder();

        behavior = BottomSheetBehavior.from(bottomSheetTeachers);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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


        buttonBottomSheetCancelTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetCoordinatorLayout.setVisibility(View.GONE);
            }
        });
        buttonBottomSheetSaveTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser parseUser = ParseUser.getCurrentUser();
                ParseObject parseObject = new ParseObject("SavedData");
                parseObject.put("username", parseUser.getUsername() + "");
                parseObject.put("savedName", name);
                parseObject.put("type", "Teacher");
                parseObject.put("pincode", parseUser.get("pincode") + "");
                parseObject.put("Location", parseUser.get("actuallocation") + "");
                parseObject.put("Phone", parseUser.get("Phone") + "");
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(StudSearchTeacherActivity.this, "Saved", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();


                        }
                    }
                });


            }
        });

        searchTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PinValidator(customEditText.getText().toString());
                LvelValidator(seletedLevel);
                if (isValidLevel && isValidPin) {
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("Regtype", "Teacher");
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
                            tvPhnNoTeacher.setText(user.get("Phone") + "");
                            tvEmailTeacher.setText(user.get("email") + "");
                            tvWebsiteTeacher.setText(user.get("website") + "");
                            tvDescTeachers.setText(user.get("description") + "");
                            tvSubOfferedTeacher.setText(user.get("courseoffered") + "");
                            tvQualificationTeacher.setText(user.get("qualification") + "");
                        }
                        BottomSheetInflater();

                    }
                }

            }
        });
    }

    private void BottomSheetInflater() {
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetCoordinatorLayout.setVisibility(View.VISIBLE);


    }


    private void LvelValidator(String seletedLevel) {
        if (seletedLevel.equals("")) {
            FancyToast.makeText(StudSearchTeacherActivity.this, "Please select teacher's level", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidLevel = false;
        } else {
            isValidLevel = true;
        }
    }

    private void PinValidator(String pin) {
        if (pin.equals("")) {
            FancyToast.makeText(StudSearchTeacherActivity.this, "Please Enter a pincode", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidPin = false;
        } else {
            isValidPin = true;

        }

    }

    private void ViewBinder() {
        customEditText = findViewById(R.id.etPincodeSearchTeacher);
        searchTeacherBtn = findViewById(R.id.searchTeacherBtn);
        spinnerLevelTeacherSearchTeacher = findViewById(R.id.spinnerLevelTeacherSearchTeacher);
        rcv = findViewById(R.id.rcvFragmentSearchTeachers);
        rcvAdaptor = new CustomAdapterRcvSearchTeacher(StudSearchTeacherActivity.this, activityList);
        bottomSheetTeachers = findViewById(R.id.bottom_sheet_lin_layout_teachers);
        bottomSheetCoordinatorLayout = findViewById(R.id.bottom_sheet_teachers);
        buttonBottomSheetSaveTeacher = findViewById(R.id.buttonBottomSheetTeacher);
        buttonBottomSheetCancelTeacher = findViewById(R.id.buttonBottomSheetCancelTeacher);
        tvDescTeachers = findViewById(R.id.tvDescTeachers);
        tvEmailTeacher = findViewById(R.id.tvEmailTeacher);
        tvPhnNoTeacher = findViewById(R.id.tvPhnNoTeacher);
        tvQualificationTeacher = findViewById(R.id.tvQualificationTeacher);
        tvSubOfferedTeacher = findViewById(R.id.tvSubOfferedTeacher);
        tvWebsiteTeacher = findViewById(R.id.tvWebsiteTeacher);


    }

}
