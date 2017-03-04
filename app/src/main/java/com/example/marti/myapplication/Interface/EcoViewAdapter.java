package com.example.marti.myapplication.Interface;

import android.app.TimePickerDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.marti.myapplication.Coms.MyCallback;
import com.example.marti.myapplication.Coms.SocketController;
import com.example.marti.myapplication.Coms.SocketProtocol;
import com.example.marti.myapplication.Model.ScheduledEcoSwitch;
import com.example.marti.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by marti on 04/03/17.
 */

public class EcoViewAdapter extends RecyclerView.Adapter<EcoViewAdapter.EcoViewHolder> {

    private ArrayList<ScheduledEcoSwitch> switching_list = new ArrayList<ScheduledEcoSwitch>();
    private Context mContext;
    private SocketProtocol protocol;


    public EcoViewAdapter(Context context, List<ScheduledEcoSwitch> list){

        this.switching_list = (ArrayList<ScheduledEcoSwitch>) list;
        this.mContext = context;

    }

    @Override
    public void onBindViewHolder(EcoViewHolder holder, int position) {

        ScheduledEcoSwitch updated_switch;

        updated_switch = switching_list.get(position);
        holder.timer_name.setText(updated_switch.getName());
        holder.selected_deadline.setText(updated_switch.getDeadline());
        holder.charging_time.setText(updated_switch.getCharging_hours());
        holder.index = position;
    }

    @Override
    public int getItemCount() {
        return switching_list.size();
    }

    @Override
    public EcoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eco_switch_layout, null);
        EcoViewHolder viewHolder = new EcoViewHolder(view);
        return viewHolder;
    }

    protected class EcoViewHolder extends RecyclerView.ViewHolder{
        protected int index;
        protected TextView timer_name;
        protected ImageButton deleteButton;
        protected TextView selected_deadline;
        protected TextView charging_time;

        protected void showNameInputDialog(){
            // get prompts.xml view
            LayoutInflater layoutInflater = LayoutInflater.from(EcoViewAdapter.this.mContext);
            View promptView = layoutInflater.inflate(R.layout.text_input_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EcoViewAdapter.this.mContext);
            alertDialogBuilder.setView(promptView);

            final EditText editText = (EditText) promptView.findViewById(R.id.text_edittext);
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

        protected void showChargingTimeInputDialog(){
            // get prompts.xml view
            LayoutInflater layoutInflater = LayoutInflater.from(EcoViewAdapter.this.mContext);
            View promptView = layoutInflater.inflate(R.layout.text_input_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EcoViewAdapter.this.mContext);
            alertDialogBuilder.setView(promptView);

            final EditText editText = (EditText) promptView.findViewById(R.id.text_edittext);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setRawInputType(Configuration.KEYBOARD_12KEY);
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

        public EcoViewHolder(View view) {
            super(view);
            LinearLayout  header_layout = (LinearLayout) view.findViewById(R.id.root_eco_switch_layout).findViewById(R.id.header_eco_switch_layout);
            this.timer_name = (TextView) header_layout.findViewById(R.id.eco_name);
            this.timer_name.setText("default");
            this.timer_name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showNameInputDialog();
                }
            });

            LinearLayout  content_layout = (LinearLayout) view.findViewById(R.id.root_eco_switch_layout).findViewById(R.id.content_eco_switch_layout);
            this.selected_deadline = (TextView) content_layout.findViewById(R.id.eco_clock_deadline);
            this.selected_deadline.setText("00:00");
            this.selected_deadline.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.v(TAG ,"Modify deadline");
                    Calendar cal = Calendar.getInstance();

                    TimePickerDialog deadlineSelector = new TimePickerDialog(mContext,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                    EcoViewHolder.this.selected_deadline.setText( hourOfDay + ":" + minute);
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
                    showChargingTimeInputDialog();
                }
            });
            this.deleteButton= (ImageButton) header_layout.findViewById(R.id.eco_button_delete);
            this.deleteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.v(TAG ,"Delete");
                    EcoViewAdapter.this.switching_list.remove(EcoViewHolder.this.index);
                    notifyDataSetChanged();
                    Log.v(TAG ,"Removed " + EcoViewHolder.this.index);
                }

            });
        }
    }


    public class Update_after_add_eco_switch implements MyCallback {

        @Override
        public void callback(boolean result) {

        }
    }

    public class Update_after_delete_eco_switch implements MyCallback {

        @Override
        public void callback(boolean result) {

        }
    }

}
