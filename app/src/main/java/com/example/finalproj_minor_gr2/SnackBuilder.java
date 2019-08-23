package com.example.finalproj_minor_gr2;

import android.graphics.Color;
import android.view.View;

import de.mateware.snacky.Snacky;

public class SnackBuilder {
    public void successSnackBuilder(String msg, View snackView) {
        Snacky.builder().setView(snackView).setBackgroundColor(Color.parseColor("#FFA726")).setText(msg).setActionText("Ok").setActionTextColor(Color.parseColor("#ffffff"))

                .setDuration(Snacky.LENGTH_INDEFINITE).setTextColor(Color.parseColor("#ffffff"))

                .success()


                .show();
    }
    public void errorSnackBuilder(String msg, View snackView) {
        Snacky.builder().setView(snackView).setBackgroundColor(Color.parseColor("#B71C1C")).setText(msg).setActionText("Ok").setActionTextColor(Color.parseColor("#ffffff"))

                .setDuration(Snacky.LENGTH_INDEFINITE).setTextColor(Color.parseColor("#ffffff"))
                .error()


                .show();
    }
    public void infoSnackBuilder(String msg, View snackView) {
        Snacky.builder().setView(snackView).setBackgroundColor(Color.parseColor("#8BC34A")).setText(msg).setActionText("Ok").setActionTextColor(Color.parseColor("#ffffff"))

                .setDuration(Snacky.LENGTH_INDEFINITE).setTextColor(Color.parseColor("#ffffff"))
                .info()


                .show();
    }
}
