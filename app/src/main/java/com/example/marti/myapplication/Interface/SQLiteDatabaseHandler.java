package com.example.marti.myapplication.Interface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marti.myapplication.Model.ScheduledEcoSwitch;
import com.example.marti.myapplication.Model.ScheduledSwitch;

/**
 * Created by Zacarias 2.0 on 04/03/2017.
 */

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Schedule";
    private static final String TABLE_ECO = "EcoSwitch";
    private static final String TABLE_SWITCH = "Switch";
    private static final String[] COLUMNS = { "NAME", "DEADLINE", "CHARGING_HOURS"};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATION_TABLE = "CREATE TABLE EcoSwitch ( "
                + "NAME TEXT PRIMARY KEY, " + "DEADLINE TEXT, "
                + "CHARGING_HOURS TEXT)";

        db.execSQL(CREATION_TABLE);

        CREATION_TABLE = "CREATE TABLE Switch (  "
                + "NAME TEXT PRIMARY KEY, " + "DEADLINE TEXT, "
                + "CHARGING_HOURS TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ECO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SWITCH);
        this.onCreate(db);
    }

    public ArrayList<ScheduledSwitch> loadSwitchData()throws Exception{
        // Get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SWITCH,COLUMNS,null,null,null,null,null,null);
        ScheduledSwitch sc;
        ArrayList result = new ArrayList<ScheduledSwitch>();
        while (cursor.moveToNext()) {
            sc = new ScheduledSwitch(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            result.add(sc);
        }
        return result;
    }

    public ArrayList<ScheduledEcoSwitch> loadEcoData()throws Exception{
        // Get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ECO,COLUMNS,null,null,null,null,null,null);
        ScheduledEcoSwitch sc;
        ArrayList result = new ArrayList<ScheduledEcoSwitch>();
        while (cursor.moveToNext()) {
            sc = new ScheduledEcoSwitch(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            result.add(sc);
        }
        return result;
    }

    public void dropSchedule(String name, Integer i) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(i==0)
        {
            db.execSQL("DELETE FROM " + TABLE_SWITCH + " WHERE NAME='"+name+"'");
        }
        else
        {
            db.execSQL("DELETE FROM " + TABLE_ECO + " WHERE NAME='"+name+"'");
        }
    }


    public void addEcoSchedule(ScheduledEcoSwitch sc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME",sc.getName());
        values.put("DEADLINE",sc.getDeadline());
        values.put("CHARGING_HOURS",sc.getCharging_hours());
        db.insert(TABLE_ECO,null,values);
    }

    public void addSwitchSchedule(ScheduledSwitch sc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME",sc.getName());
        values.put("DEADLINE",sc.getEnd_time());
        values.put("CHARGING_HOURS",sc.getStart_time());
        db.insert(TABLE_SWITCH,null,values);
    }

}
