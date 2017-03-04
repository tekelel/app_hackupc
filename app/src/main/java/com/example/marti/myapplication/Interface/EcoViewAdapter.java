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
    private View parent;
    private View recycler;
    private SocketProtocol protocol;


    public EcoViewAdapter(View parent, View recycler, List<ScheduledEcoSwitch> list){

        this.switching_list = (ArrayList<ScheduledEcoSwitch>) list;
        this.parent = parent;
        this.recycler = recycler;
        EcoCreator creator = new EcoCreator(parent, this);
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
        try {
            return switching_list.size();
        } catch(Exception e){
            return 0;
        }
    }

    @Override
    public EcoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eco_switch_layout, parent, false);
        EcoViewHolder viewHolder = new EcoViewHolder(view);
        return viewHolder;
    }

    protected class EcoViewHolder extends RecyclerView.ViewHolder{
        protected int index;
        protected TextView timer_name;
        protected ImageButton deleteButton;
        protected TextView selected_deadline;
        protected TextView charging_time;

        public EcoViewHolder(View view) {
            super(view);
            LinearLayout  header_layout = (LinearLayout) view.findViewById(R.id.root_eco_switch_layout).findViewById(R.id.header_eco_switch_layout);
            this.timer_name = (TextView) header_layout.findViewById(R.id.eco_name);

            LinearLayout  content_layout = (LinearLayout) view.findViewById(R.id.root_eco_switch_layout).findViewById(R.id.content_eco_switch_layout);
            this.selected_deadline = (TextView) content_layout.findViewById(R.id.eco_clock_deadline);

            this.charging_time = (TextView) content_layout.findViewById(R.id.eco_charging_time);

            this.deleteButton= (ImageButton) header_layout.findViewById(R.id.eco_button_delete);

        }
    }


    public class Update_after_add_prog_switch implements MyCallback {

        @Override
        public void callback(boolean result) {

        }
    }

    public class Update_after_delete_prog_switch implements MyCallback {

        @Override
        public void callback(boolean result) {

        }
    }

}
