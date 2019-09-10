package com.example.finalproj_minor_gr2.student;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproj_minor_gr2.Adapters.CustomAdapterRcvSearchTeacher;
import com.example.finalproj_minor_gr2.R;
import com.example.finalproj_minor_gr2.model_classes.ModelClassDemoSearchTeacher;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedTeachersFragment extends Fragment {

    CustomAdapterRcvSearchTeacher adapterRcvSearchTeacher;
    RecyclerView rcvSavedTeacher;
    ArrayList<ModelClassDemoSearchTeacher> activitylist = new ArrayList<>();
    ModelClassDemoSearchTeacher modelClassDemoSearchTeacher;

    public SavedTeachersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_teachers, container, false);
        BindViews(view);
        ParseFetchData();
        return view;


    }

    private void ParseFetchData() {
        String username;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("SavedData");
        ParseUser parseUser = ParseUser.getCurrentUser();
        username = parseUser.getUsername();
        query.whereEqualTo("username", username);
        query.whereEqualTo("type", "Teacher");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects != null) {
                    for (ParseObject parseObject : objects) {
                        modelClassDemoSearchTeacher = new ModelClassDemoSearchTeacher();
                        modelClassDemoSearchTeacher.setActivityName(parseObject.get("savedName").toString());
                        activitylist.add(modelClassDemoSearchTeacher);

                    }
                    rcvSavedTeacher.setAdapter(adapterRcvSearchTeacher);


                }
            }
        });
    }

    private void BindViews(View view) {
        rcvSavedTeacher = view.findViewById(R.id.rcvSavedTeachers);
        adapterRcvSearchTeacher = new CustomAdapterRcvSearchTeacher(getContext(), activitylist);

    }

}
