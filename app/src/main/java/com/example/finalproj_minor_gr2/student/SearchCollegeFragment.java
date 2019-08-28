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
public class SearchCollegeFragment extends Fragment {
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

    public SearchCollegeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_college, container, false);
        ViewBinder(view);
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
        return view;

    }

    private void LvelValidator(String seletedLevel) {
        if (seletedLevel.equals("")) {
            FancyToast.makeText(getContext(), "Please select college's level", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
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
        customEditText = view.findViewById(R.id.etPincodeSearchCollege);
        searchCollegeBtn = view.findViewById(R.id.searchCollegeBtn);
        spinnerLevelTeacherSearchCollege = view.findViewById(R.id.spinnerLevelTeacherSearchCollege);
        rcv = view.findViewById(R.id.rcvFragmentSearchCollege);
        rcvAdaptor = new CustomAdapterRcvSearchTeacher(view.getContext(), activityList);


    }

}
