package com.example.marti.myapplication.Model;

/**
 * Created by marti on 04/03/17.
 */

public class ScheduledEcoSwitch extends ScheduledAction {
    private String deadline;
    private String charging_hours;

    public ScheduledEcoSwitch(String name, String deadline, String charging_hours){
        super(name);
        this.deadline = deadline;
        this.charging_hours = charging_hours;
    }

    public String getDeadline(){
        return  deadline;
    }

    public String getCharging_hours() {
        return charging_hours;
    }

    public void setCharging_hours(String charging_hours) {
        this.charging_hours = charging_hours;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
