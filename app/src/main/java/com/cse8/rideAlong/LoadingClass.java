package com.cse8.rideAlong;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingClass {

    Activity activity;
    AlertDialog dialog;

    public LoadingClass(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_loding_layout, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    public void stopDialog() {
        dialog.dismiss();
    }
}
