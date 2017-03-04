package com.example.marti.myapplication.Interface;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marti.myapplication.Coms.MyCallback;
import com.example.marti.myapplication.Model.ScheduledEcoSwitch;
import com.example.marti.myapplication.Model.ScheduledSwitch;
import com.example.marti.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marti on 04/03/17.
 */

public class ProgramViewAdapter extends RecyclerView.Adapter<ProgramViewAdapter.ProgrammedViewHolder> {

    private ArrayList<ScheduledSwitch> switching_list = new ArrayList<ScheduledSwitch>();
    private Context mContext;

    public ProgramViewAdapter(Context context, List<ScheduledSwitch> list){

        this.switching_list = (ArrayList<ScheduledSwitch>) list;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(ProgrammedViewHolder holder, int position) {
        ScheduledSwitch updated_switch;

        updated_switch = switching_list.get(position);
        holder.textView.setText(updated_switch.getName());
    }

    @Override
    public int getItemCount() {
        return switching_list.size();
    }

    @Override
    public ProgrammedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prog_switch_layout, null);
        ProgrammedViewHolder viewHolder = new ProgrammedViewHolder(view);
        return viewHolder;

    }

    protected class ProgrammedViewHolder extends RecyclerView.ViewHolder{
        protected TextView textView;

        public ProgrammedViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.prog_text);
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
