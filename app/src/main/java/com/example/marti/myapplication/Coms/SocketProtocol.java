package com.example.marti.myapplication.Coms;

/**
 * Created by marti on 04/03/17.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

import java.io.PrintWriter;

// Definir los siguientes parametros

// SERVER_IP, SERVERPORT



public class SocketProtocol {


    static public Socket connect_to_server() throws IOException{
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("34.249.135.120", 12345), 3000);
        return socket;
    }

    static public boolean send_init(){

        try {
            Socket s = connect_to_server();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

            out.print("init/state");
            out.flush();

            String status_string = in.readLine();

            if(status_string.equals("on"))
                return true;
            else
                return false;

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }

    }

    static public boolean send_on(){

        try {

            Socket s = connect_to_server();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

            out.print("switch/on");
            out.flush();

            String status_string = in.readLine();
            return true;

        } catch (UnknownHostException e1) {
            return false;
        } catch (IOException e1) {
            return false;
        }

    }

    static public boolean send_off(){
        try {

            Socket s = connect_to_server();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

            out.print("switch/off");
            out.flush();

            String status_string = in.readLine();
            return true;

        } catch (UnknownHostException e1) {
            return false;
        } catch (IOException e1) {
            return false;
        }
    }

    static public boolean add_program_switch(String name, String start_time, String stop_time) {
        try {
            Socket s = connect_to_server();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

            out.print("command/sched/" + start_time.split(":")[0] + "/" + stop_time.split(":")[0]);
            out.flush();

            String status_string = in.readLine();
            return true;
        } catch (UnknownHostException e1) {
            return false;
        } catch (IOException e1) {
            return false;
        }
    }

    static public void delete_program_switch(String name, MyCallback callback ){

    }

    static public boolean add_eco_switch(String name, String deadline, String charge_time){

        try {
            Socket s = connect_to_server();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

            out.print("command/eco/" + deadline.split(":")[0] + "/" + charge_time.split(":")[0]);
            out.flush();

            String status_string = in.readLine();

            return true;

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }

    }
    static public void delete_eco_switch(String name, MyCallback callback ){

    }
}
