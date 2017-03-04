package com.example.marti.myapplication.Model;

/**
 * Created by marti on 04/03/17.
 */

public class ScheduledSwitch extends ScheduledAction {
    private String start_time;
    private String end_time;

    public ScheduledSwitch(String name, String start_time, String end_time){
        super(name);
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
