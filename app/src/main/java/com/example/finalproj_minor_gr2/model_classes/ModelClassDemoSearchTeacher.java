package com.example.finalproj_minor_gr2.model_classes;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class ModelClassDemoSearchTeacher {
   String activityName;
   int activityIcon;
   String activityLocation;
   String desc,dest;
   String color;
    Drawable background;

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getColor() {
        return color;
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityIcon() {
        return activityIcon;
    }

    public void setActivityIcon(int activityIcon) {
        this.activityIcon = activityIcon;
    }
}
