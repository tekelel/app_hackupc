package com.example.marti.myapplication.Interface;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.marti.myapplication.Coms.MyCallback;
import com.example.marti.myapplication.Model.ScheduledEcoSwitch;
import com.example.marti.myapplication.Model.ScheduledSwitch;
import com.example.marti.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by marti on 04/03/17.
 */

public class ProgramViewAdapter extends RecyclerView.Adapter<ProgramViewAdapter.ProgrammedViewHolder> {

    private ArrayList<ScheduledSwitch> switching_list = new ArrayList<ScheduledSwitch>();
    private View parent;
    private View recycler;
    private final ThreadLocal<SQLiteDatabaseHandler> handler = new ThreadLocal<>();
    private int lastPosition;

    public ProgramViewAdapter(View parent, View recycler , List<ScheduledSwitch> list, SQLiteDatabaseHandler handler){

        this.switching_list = (ArrayList<ScheduledSwitch>) list;
        this.parent = parent;
        this.recycler = recycler;
        this.handler.set(handler);
        ProgramCreator creator = new ProgramCreator(parent, this, handler);
    }

    private void setScaleAnimation(View view) {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_in_left);
        view.startAnimation(animation);
    }

    @Override
    public void onBindViewHolder(ProgrammedViewHolder holder, int position) {
        ScheduledSwitch updated_switch;
        updated_switch = switching_list.get(position);
        holder.timer_name.setText(updated_switch.getName());
        holder.start_time.setText(updated_switch.getStart_time());
        holder.stop_time.setText(updated_switch.getEnd_time());

        holder.index = position;

        setScaleAnimation(holder.itemView);
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
    public ProgrammedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prog_switch_layout, parent, false);
        ProgrammedViewHolder viewHolder = new ProgrammedViewHolder(view);
        return viewHolder;

    }

    public void addElement(ScheduledSwitch sched_switch) {
        switching_list.add(sched_switch);
        notifyItemInserted(getItemCount());
    }

    protected class ProgrammedViewHolder extends RecyclerView.ViewHolder{
        protected int index;
        protected TextView timer_name;
        protected ImageButton deleteButton;
        protected TextView start_time;
        protected TextView stop_time;

        public ProgrammedViewHolder(View view) {
            super(view);
            LinearLayout header_layout = (LinearLayout) view.findViewById(R.id.root_prog_switch_layout).findViewById(R.id.header_prog_switch_layout);
            this.timer_name = (TextView) header_layout.findViewById(R.id.prog_name);

            LinearLayout  content_layout = (LinearLayout) view.findViewById(R.id.root_prog_switch_layout).findViewById(R.id.content_prog_switch_layout);
            this.start_time = (TextView) content_layout.findViewById(R.id.prog_clock_start);

            this.stop_time = (TextView) content_layout.findViewById(R.id.prog_clock_stop);

            this.deleteButton= (ImageButton) header_layout.findViewById(R.id.prog_button_delete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handler.get().dropSchedule(switching_list.get(index).getName(),0);
                        switching_list.remove(index);
                        ProgramViewAdapter.this.notifyItemRemoved(index);
                    }
                }
            );

        }

    }

    public class Update_after_add_program_switch implements MyCallback {

        @Override
        public void callback(boolean result) {

        }
    }

    public class Update_after_delete_program_switch implements MyCallback {

        @Override
        public void callback(boolean result) {

        }
    }

}
