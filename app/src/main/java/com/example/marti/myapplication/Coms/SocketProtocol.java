package com.example.marti.myapplication.Coms;

/**
 * Created by marti on 04/03/17.
 */

public class SocketProtocol {

    private final int server_port = 12345;
    private final String server_url = "ec2-34-249-135-120.eu-west-1.compute.amazonaws.com";

    static public void send_init(MyCallback callback){

    }

    static public void send_on(MyCallback callback){

    }

    static public void send_off(MyCallback callback){

    }

    static public void add_program_switch(String name, String start_time, String stop_time,MyCallback callback ){

    }

    static public void delete_program_switch(String name, MyCallback callback ){

    }

    static public void add_eco_switch(String name, String deadline, String charge_time, MyCallback callback ){

    }
    static public void delete_eco_switch(String name, MyCallback callback ){

    }
}
