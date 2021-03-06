package com.example.zeetvideo;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;

public class AlertBox {
   private Activity activity;
   private AlertDialog alertDialog;
    AlertBox(Activity myActivity){
        activity=myActivity;
    }
    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }
    void dismissDialog(){
        alertDialog.dismiss();
    }
}
