package com.example.marti.myapplication.Coms;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.AbstractCollection;

/**
 * Created by marti on 04/03/17.
 */

public class SocketProtocolOperation extends AsyncTask<String, Void, String> {
    public static final int INIT = 0;
    public static final int SEND_ON = 1;
    public static final int SEND_OFF = 2;
    public static final int CREATE_ECO = 3;
    public static final int CREATE_PRO = 4;


    private MyCallback callback;
    private boolean operation_result;
    private int operation;
    private String[] args;

    public SocketProtocolOperation(MyCallback callback, int operation, String[] args){
        this.callback = callback;
        this.operation_result = false;
        this.operation = operation;
        this.args = args;
    }

    @Override
    protected String doInBackground(String... strings) {
        switch(operation){
            case INIT:
                operation_result = SocketProtocol.send_init();
                break;
            case SEND_ON:
                operation_result = SocketProtocol.send_on();
                break;
            case SEND_OFF:
                operation_result = SocketProtocol.send_off();
                break;
            case CREATE_ECO:
                operation_result = SocketProtocol.add_eco_switch(args[0], args[2], args[1]);
                break;
            case CREATE_PRO:
                operation_result = SocketProtocol.add_program_switch(args[0], args[1], args[2]);
                break;
            default:
                operation_result = false;
                break;
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        callback.callback(operation_result);
    }

}
