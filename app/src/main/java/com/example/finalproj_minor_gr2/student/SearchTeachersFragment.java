package com.example.finalproj_minor_gr2.student;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.finalproj_minor_gr2.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.libizo.CustomEditText;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTeachersFragment extends Fragment implements View.OnClickListener {
    CustomEditText customEditText;
    Button searchTeacherBtn;
    MaterialSpinner spinnerLevelTeacherSearchTeacher;
    String[] levelTeacherSearchTeacher = {"Select level", "Primary School", "High School", "Higher Secondary School", "Btech", "BCA"};
    String seletedLevel="";
    boolean isValidPin = false, isValidLevel = false;

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
            public void onClick(View view) {
                PinValidator(customEditText.getText().toString());
                LvelValidator(seletedLevel);
                if (isValidLevel&&isValidPin){
                    Toast.makeText(getContext(), "Successs", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;

    }

    private void LvelValidator(String seletedLevel) {
        if (seletedLevel.equals("")) {
            FancyToast.makeText(getContext(), "Please select teacher's level", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            isValidLevel=false;
        }else {
            isValidLevel=true;
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

    }


    @Override
    public void onClick(View view) {

    }
}
