package com.example.marti.myapplication.Interface;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.marti.myapplication.Coms.MyCallback;
import com.example.marti.myapplication.R;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * Created by marti on 04/03/17.
 */

class ProgramCreator implements View.OnClickListener, MyCallback {

    private View parent;
    private ProgramViewAdapter adapter;
    protected TextView timer_name;
    protected ImageButton deleteButton;
    protected TextView start_time;
    protected TextView stop_time;
    protected SQLiteDatabaseHandler handler;

    public ProgramCreator(View parent, ProgramViewAdapter adapter, SQLiteDatabaseHandler handler) {
        this.parent = parent;
        this.adapter = adapter;
        this.handler = handler;

        FloatingActionButton fab = (FloatingActionButton) parent.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    protected void showNameInputDialog(View parent_dialog){
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(parent_dialog.getContext());
        View promptView = layoutInflater.inflate(R.layout.text_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent_dialog.getContext());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.text_edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        timer_name.setText(editText.getText());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public void onClick(final View view) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View promptView = layoutInflater.inflate(R.layout.prog_switch_layout, null);

        LinearLayout header_layout = (LinearLayout) promptView.findViewById(R.id.root_prog_switch_layout).findViewById(R.id.header_prog_switch_layout);
        this.timer_name = (TextView) header_layout.findViewById(R.id.prog_name);
        this.timer_name.setText("default");
        this.timer_name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Click on prog name.\n");
                showNameInputDialog(promptView);

            }
        });

        LinearLayout content_layout = (LinearLayout) promptView.findViewById(R.id.root_prog_switch_layout).findViewById(R.id.content_prog_switch_layout);
        this.start_time = (TextView) content_layout.findViewById(R.id.prog_clock_start);
        this.start_time.setText("00:00");
        this.start_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Modify start");
                Calendar cal = Calendar.getInstance();

                TimePickerDialog deadlineSelector = new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                start_time.setText(hourOfDay + ":" + minute);
                            }
                        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);
                deadlineSelector.setTitle("Select your charging deadline.");
                deadlineSelector.show();
            }
        });

        this.stop_time = (TextView) content_layout.findViewById(R.id.prog_clock_stop);
        this.stop_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Modify stop");
                Calendar cal = Calendar.getInstance();

                TimePickerDialog deadlineSelector = new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                stop_time.setText(hourOfDay + ":" + minute);
                            }
                        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);
                deadlineSelector.setTitle("Select your charging deadline.");
                deadlineSelector.show();
            }
        });
        this.deleteButton = (ImageButton) header_layout.findViewById(R.id.prog_button_delete);
        header_layout.removeView(this.deleteButton);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent.getContext());
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v(TAG,"Adding new Prog scheduler with name " + timer_name.getText() + " and startTime " + start_time.getText()
                                + " and stop time " + stop_time.getText());
                                /* --------------------------- INSERTAR LOGICA DE BASE DE DATOS AQUIIIIIIII --------------------------------------*/

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.v(TAG,"Canceling addition.");
                                dialog.cancel();
                            }
                        });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }




    @Override
    public void callback(boolean result) {

    }
}