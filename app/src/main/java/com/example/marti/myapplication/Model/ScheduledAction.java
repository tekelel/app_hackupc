package com.example.marti.myapplication.Model;

/**
 * Created by marti on 04/03/17.
 */

public abstract class ScheduledAction {
    private String name;

    public ScheduledAction(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
