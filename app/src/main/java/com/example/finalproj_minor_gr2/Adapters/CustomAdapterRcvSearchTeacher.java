package com.example.finalproj_minor_gr2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproj_minor_gr2.R;
import com.example.finalproj_minor_gr2.model_classes.ModelClassDemoSearchTeacher;

import java.util.ArrayList;
import java.util.Random;


public class CustomAdapterRcvSearchTeacher extends RecyclerView.Adapter {
    Context context;
    ArrayList<ModelClassDemoSearchTeacher> menuList;
    public OnItemLongClickListener mItemLongClickListener;
    public OnItemClickListener mItemClickListener;



    public CustomAdapterRcvSearchTeacher(Context context, ArrayList<ModelClassDemoSearchTeacher> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcv_searched_teachers_list, viewGroup, false);
        return new ViewHolder(v);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final ModelClassDemoSearchTeacher name = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.tvTeachersName.setText((name.getActivityName()));
            genericViewHolder.tvTeacherActualAddress.setText(name.getActivityLocation());
            genericViewHolder.linearLayout.setBackgroundResource(name.getResource());




            //genericViewHolder.imgView.setImageResource(name.getActivityIcon());
            // genericViewHolder.tvName.setText(name.getActivityName());
        }
    }


    //    need to override this method
    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }
    private ModelClassDemoSearchTeacher getItem(int position) {
        return menuList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ArrayList<ModelClassDemoSearchTeacher> menulist);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position, ArrayList<ModelClassDemoSearchTeacher> names);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTeachersName;
        private TextView tvTeacherActualAddress;
        private ImageView imgView;
        private View rcvLinearLayout;
        LinearLayout linearLayout;


        ViewHolder(final View itemView) {
            super(itemView);

            this.tvTeachersName = itemView.findViewById(R.id.tvRcvTeacherUname);
            //this.rcvLinearLayout=itemView.findViewById(R.id.rcvLinLayout);

            //this.imgView = itemView.findViewById(R.id.icon);
            this.tvTeacherActualAddress=itemView.findViewById(R.id.tvActualAddressSearchTeacher);
            this.linearLayout=itemView.findViewById(R.id.rcvlinlayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), menuList);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mItemLongClickListener.onItemLongClick(itemView, getAdapterPosition(), menuList);

                    return true;
                }
            });

        }
    }
}
