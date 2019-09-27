package com.example.finalproj_minor_gr2.student;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproj_minor_gr2.Adapters.CustomAdapterRcvSearchTeacher;
import com.example.finalproj_minor_gr2.R;
import com.example.finalproj_minor_gr2.model_classes.ModelClassDemoSearchTeacher;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FollowedUsersActivity extends AppCompatActivity {
    RecyclerView rcv_followed_users;
    CustomAdapterRcvSearchTeacher customAdapterRcvSearchTeacher;
    ModelClassDemoSearchTeacher modelClassDemoSearchTeacher;
    ArrayList<ModelClassDemoSearchTeacher> list = new ArrayList();
    List<String> followers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followed_users);
        BindViews();
        ParseFetchData();
        List currentfollowing = ParseUser.getCurrentUser().getList("following");
        try {


            for (int i = 0; i < currentfollowing.size(); i++) {
                modelClassDemoSearchTeacher = new ModelClassDemoSearchTeacher();
                modelClassDemoSearchTeacher.setActivityName(currentfollowing.get(i) + "");
                list.add(modelClassDemoSearchTeacher);

            }
            rcv_followed_users.setAdapter(customAdapterRcvSearchTeacher);
        } catch (Exception e) {

        }

    }

    private void ParseFetchData() {
       /* try {
            ParseQuery<ParseUser>query=ParseUser.getQuery();
            query.whereEqualTo("username",ParseUser.getCurrentUser());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e==null){
                       followers= ParseUser.getCurrentUser().getList("following");
                       for (int i=0;i<followers.size();i++){
                           modelClassDemoSearchTeacher=new ModelClassDemoSearchTeacher();
                           modelClassDemoSearchTeacher.setActivityName(followers.get(i));
                           list.add(modelClassDemoSearchTeacher);
                       }
                       rcv_followed_users.setAdapter(customAdapterRcvSearchTeacher);

                    }

                }
            });
    }catch (Exception e){

        }*/

    }

    private void BindViews() {
        rcv_followed_users = findViewById(R.id.rcv_followed_users);
        customAdapterRcvSearchTeacher = new CustomAdapterRcvSearchTeacher(FollowedUsersActivity.this, list);


    }
}
