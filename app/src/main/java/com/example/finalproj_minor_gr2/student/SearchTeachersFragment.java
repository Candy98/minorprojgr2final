package com.example.finalproj_minor_gr2.student;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTeachersFragment extends Fragment implements View.OnClickListener {
    CustomEditText customEditText;
    String unameChecker,getUnameCheckerNext;
    Button searchTeacherBtn;
    MaterialSpinner spinnerLevelTeacherSearchTeacher;
    String[] levelTeacherSearchTeacher = {"Select level", "Primary School", "High School", "Higher Secondary School", "Btech", "BCA"};
    String seletedLevel = "";
    boolean isValidPin = false, isValidLevel = false;
    ArrayList<ModelClassDemoSearchTeacher> activityList = new ArrayList<>();
    RecyclerView rcv;
    CustomAdapterRcvSearchTeacher rcvAdaptor;
    ModelClassDemoSearchTeacher modelClassDemo;

    public SearchTeachersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_teachers, container, false);
        ViewBinder(view);
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
                                        modelClassDemo=new ModelClassDemoSearchTeacher();

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
        return view;

    }

    private void LvelValidator(String seletedLevel) {
        if (seletedLevel.equals("")) {
            FancyToast.makeText(getContext(), "Please select teacher's level", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidLevel = false;
        } else {
            isValidLevel = true;
        }
    }

    private void PinValidator(String pin) {
        if (pin.equals("")) {
            FancyToast.makeText(getContext(), "Please Enter a pincode", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidPin = false;
        } else {
            isValidPin = true;

        }

    }

    private void ViewBinder(View view) {
        customEditText = view.findViewById(R.id.etPincodeSearchTeacher);
        searchTeacherBtn = view.findViewById(R.id.searchTeacherBtn);
        spinnerLevelTeacherSearchTeacher = view.findViewById(R.id.spinnerLevelTeacherSearchTeacher);
        rcv = view.findViewById(R.id.rcvFragmentSearchTeachers);
        rcvAdaptor = new CustomAdapterRcvSearchTeacher(view.getContext(), activityList);


    }


    @Override
    public void onClick(View view) {

    }
}
