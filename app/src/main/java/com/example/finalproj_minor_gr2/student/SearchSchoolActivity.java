package com.example.finalproj_minor_gr2.student;

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
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;
import java.util.List;

public class SearchSchoolActivity extends AppCompatActivity {
    CustomEditText customEditText;
    String unameChecker, getUnameCheckerNext;
    MKLoader mkLoader;
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
                mkLoader.setVisibility(View.VISIBLE);

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
                                    mkLoader.setVisibility(View.INVISIBLE);
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
        mkLoader=findViewById(R.id.loaderSearchTeacher);


    }


}
