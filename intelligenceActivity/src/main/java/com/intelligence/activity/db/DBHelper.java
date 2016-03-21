package com.intelligence.activity.db;

import android.content.Context;  
import android.database.sqlite.SQLiteDatabase;  
import android.database.sqlite.SQLiteOpenHelper;  
  
public class DBHelper extends SQLiteOpenHelper {  
  
    private static final String DATABASE_NAME = "kettyhum.db";  
    private static final int DATABASE_VERSION = 1;  
      
    public DBHelper(Context context) {  
        //CursorFactory设置为null,使用默认值  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }  
  
    //数据库第一次被创建时onCreate会被调用  
    @Override  
    public void onCreate(SQLiteDatabase db) {  
//        db.execSQL("CREATE TABLE IF NOT EXISTS person" +  
//                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age INTEGER, info TEXT)");
    	
        String sql = "CREATE TABLE if not exists timing (id integer PRIMARY KEY AUTOINCREMENT,'custom_id' integer,'c_hour' integer DEFAULT 0,'c_min' integer DEFAULT 0,repeat text DEFAULT '0',title text,'begin_remind' integer DEFAULT 0,'end_remind' integer DEFAULT 0,'water_remind' integer DEFAULT 0,'water_level' float DEFAULT 1.0,'t_open' integer DEFAULT 0,'t_begin_time' integer DEFAULT 0,'t_h_open' integer DEFAULT 0,'orderid' text,machineid text NOT NULL)";
        db.execSQL(sql);
        
        
        
    }  
  
    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");  
    }  
}  
