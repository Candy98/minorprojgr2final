package com.example.finalproj_minor_gr2.message;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproj_minor_gr2.R;

import java.util.ArrayList;


public class MessageNameLIstActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    private RecyclerViewAdapter mAdapter;

    private ArrayList<AbstractModelMessageNameActivity> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_name_list2);

        // ButterKnife.bind(this);
        findViews();
        setAdapter();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }


    private void setAdapter() {


        modelList.add(new AbstractModelMessageNameActivity("Android", "Hello " + " Android"));
        modelList.add(new AbstractModelMessageNameActivity("Beta", "Hello " + " Beta"));
        modelList.add(new AbstractModelMessageNameActivity("Cupcake", "Hello " + " Cupcake"));
        modelList.add(new AbstractModelMessageNameActivity("Donut", "Hello " + " Donut"));
        modelList.add(new AbstractModelMessageNameActivity("Eclair", "Hello " + " Eclair"));
        modelList.add(new AbstractModelMessageNameActivity("Froyo", "Hello " + " Froyo"));
        modelList.add(new AbstractModelMessageNameActivity("Gingerbread", "Hello " + " Gingerbread"));
        modelList.add(new AbstractModelMessageNameActivity("Honeycomb", "Hello " + " Honeycomb"));
        modelList.add(new AbstractModelMessageNameActivity("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
        modelList.add(new AbstractModelMessageNameActivity("Jelly Bean", "Hello " + " Jelly Bean"));
        modelList.add(new AbstractModelMessageNameActivity("KitKat", "Hello " + " KitKat"));
        modelList.add(new AbstractModelMessageNameActivity("Lollipop", "Hello " + " Lollipop"));
        modelList.add(new AbstractModelMessageNameActivity("Marshmallow", "Hello " + " Marshmallow"));
        modelList.add(new AbstractModelMessageNameActivity("Nougat", "Hello " + " Nougat"));
        modelList.add(new AbstractModelMessageNameActivity("Android O", "Hello " + " Android O"));


        mAdapter = new RecyclerViewAdapter(MessageNameLIstActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, AbstractModelMessageNameActivity model) {

                //handle item click events here
                Toast.makeText(MessageNameLIstActivity.this, "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });


    }


}
