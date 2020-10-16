package com.alyhatem.craver;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.google.android.gms.dynamite.DynamiteModule;

public class LoaderDialog {
    Activity activity;
    AlertDialog dialog;
    public LoaderDialog(Activity myActivity){
        activity=myActivity;

    }
    void startDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading,null));
        builder.setCancelable(false);
        dialog=builder.create();
        dialog.show();

    }
    void dismissDialog(){
        dialog.dismiss();
    }

}
