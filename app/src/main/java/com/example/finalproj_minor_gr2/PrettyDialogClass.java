package com.example.finalproj_minor_gr2;

import android.content.Context;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class PrettyDialogClass {
    PrettyDialog prettyDialog;

    public void showDiag(String message, Context context) {
        prettyDialog = new PrettyDialog(context);
        prettyDialog.setTitle("PrettyDialog Title")
                .setMessage("PrettyDialog Message")
                .show();

    }

    public void dismissDiag() {
        prettyDialog.dismiss();
    }
}
