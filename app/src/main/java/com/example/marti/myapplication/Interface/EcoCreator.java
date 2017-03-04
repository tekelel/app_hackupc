package com.example.marti.myapplication.Interface;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by marti on 04/03/17.
 */

public class EcoCreator implements DialogInterface.OnClickListener {
    private Context mContext;
    private View parent;

    public EcoCreator(Context context, View parent){
        mContext = context;
        this.parent = parent;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
