package com.example.marti.myapplication.Interface;

import android.app.TimePickerDialog;
import android.content.Context;
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
import com.example.marti.myapplication.Coms.SocketProtocol;
import com.example.marti.myapplication.Coms.SocketProtocolOperation;
import com.example.marti.myapplication.Model.ScheduledEcoSwitch;
import com.example.marti.myapplication.R;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * Created by marti on 04/03/17.
 */

public class EcoCreator implements View.OnClickListener, MyCallback {
    private Context mContext;
    private View parent;
    private EcoViewAdapter adapter;
    protected TextView timer_name;
    protected TextView selected_deadline;
    protected TextView charging_time;
    protected  SQLiteDatabaseHandler handler;

    public EcoCreator(View parent, EcoViewAdapter adapter, SQLiteDatabaseHandler handler){
        this.parent = parent;
        this.adapter = adapter;
        this.handler = handler;

        FloatingActionButton fab = (FloatingActionButton) parent.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View promptView = layoutInflater.inflate(R.layout.eco_switch_layout, null);

        LinearLayout header_layout = (LinearLayout) promptView.findViewById(R.id.root_eco_switch_layout).findViewById(R.id.header_eco_switch_layout);
        this.timer_name = (TextView) header_layout.findViewById(R.id.eco_name);
        this.timer_name.setText("default");
        this.timer_name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showNameInputDialog(promptView);
            }
        });

        LinearLayout  content_layout = (LinearLayout) promptView.findViewById(R.id.root_eco_switch_layout).findViewById(R.id.content_eco_switch_layout);
        this.selected_deadline = (TextView) content_layout.findViewById(R.id.eco_clock_deadline);
        this.selected_deadline.setText("00:00");
        this.selected_deadline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG ,"Modify deadline");
                Calendar cal = Calendar.getInstance();

                TimePickerDialog deadlineSelector = new TimePickerDialog(promptView.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                EcoCreator.this.selected_deadline.setText( hourOfDay + ":" + minute);
                            }
                        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);
                deadlineSelector.setTitle("Select your charging deadline.");
                deadlineSelector.show();
            }
        });

        this.charging_time = (TextView) content_layout.findViewById(R.id.eco_charging_time);
        this.charging_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG ,"Modify charging time");
                showChargingTimeInputDialog(promptView);
            }
        });

        ImageButton close_button = (ImageButton) header_layout.findViewById(R.id.eco_button_delete);
        header_layout.removeView(close_button);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent.getContext());
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v(TAG,"Enviada request de crear un scheduler ECO.");
                        String[] args = {timer_name.getText().toString(),
                                        selected_deadline.getText().toString(),
                                        charging_time.getText().toString()
                        };
                        SocketProtocolOperation initOp = new SocketProtocolOperation(EcoCreator.this ,SocketProtocolOperation.CREATE_ECO, args);
                        initOp.execute("");
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

    protected void showChargingTimeInputDialog(View parentDialog){
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(parentDialog.getContext());
        View promptView = layoutInflater.inflate(R.layout.text_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parentDialog.getContext());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.text_edittext);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setRawInputType(Configuration.KEYBOARD_12KEY);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        charging_time.setText(editText.getText());
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
    public void callback(boolean result) {
        Log.v(TAG,"Adding new Eco scheduler with name " + EcoCreator.this.timer_name.getText() + " and deadline " + EcoCreator.this.selected_deadline.getText()
                + " and charging time " + EcoCreator.this.charging_time.getText());
        handler.addEcoSchedule(new ScheduledEcoSwitch(EcoCreator.this.timer_name.getText().toString(),EcoCreator.this.selected_deadline.getText().toString(),EcoCreator.this.charging_time.getText().toString()));

        adapter.addElement(new ScheduledEcoSwitch(timer_name.getText().toString(), selected_deadline.getText().toString(), charging_time.getText().toString()));
    }
}
