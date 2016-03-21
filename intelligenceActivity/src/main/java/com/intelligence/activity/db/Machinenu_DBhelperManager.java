package com.intelligence.activity.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;

/**
 * 设备的数据管理类
 * 
 * @author Administrator
 * 
 */
public class Machinenu_DBhelperManager {
	/**
	 * 数据库名
	 */
	private static final String DATABASE_NAME = "macin_quickdial.db";

	/**
	 * 数据表名
	 */
	private static final String DATABASE_TABLE = "macin_quickdial";

	/**
	 * 数据库版本
	 */
	private static final int DATABASE_VERSION = 1;

	/**
	 * 应用ID
	 */
	private static final String MAC                = "MAC";
	
	
	private static final String IS_DELE              = "IS_DELE";
	
	private static final String MNAME                = "MNAME";
	
	private static final String M_ID                = "M_ID";
	
	private static final String M_IP                = "M_IP";
	
	private static final String IS_UPDATA                = "IS_UPDATA";
	
	private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + 
	" (key_id integer default '1' not null primary key autoincrement, "
			+ "MAC text  not null, "
			+ "MNAME text not null, "
			+ "M_ID text not null, "
			+ "M_IP text not null, "
			+ "IS_UPDATA integer, "
			+ "IS_DELE integer )" ;
	
	/**
     * 
     */
	private final Context context;

	/**
     * 
     */
	private DatabaseHelper DBHelper;

	/**
     * 
     */
	private SQLiteDatabase db;
	/**
	 * 单例
	 */
	private static Machinenu_DBhelperManager instance;

	public Machinenu_DBhelperManager(final Context ctx) {
		context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	public static Machinenu_DBhelperManager getInstance(Context context) {
		if (instance == null)
			instance = new Machinenu_DBhelperManager(context);
		return instance;
	}

	public synchronized List<MACHINDATA> getAlermList(int state) {
		List<MACHINDATA> list = new ArrayList<Machinenu_DBhelperManager.MACHINDATA>();

		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = open();
			cursor = getAll();
			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				MACHINDATA data = new MACHINDATA();
				data.MAC = cursor.getString(cursor.getColumnIndex(MAC));
				data.IS_DELE = cursor.getInt(cursor.getColumnIndex(IS_DELE));
				data.MNAME = cursor.getString(cursor.getColumnIndex(MNAME));
				data.M_ID = cursor.getString(cursor.getColumnIndex(M_ID));
				data.M_IP = cursor.getString(cursor.getColumnIndex(M_IP));
				data.IS_UPDATA = cursor.getInt(cursor.getColumnIndex(IS_UPDATA));
				if(data.IS_DELE == state)
				list.add(data);
			}
		} catch (Exception e) {
			// TODO: handle exceptionn
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return list;
	}
	

	private static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			if(oldVersion != newVersion){
				db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
				onCreate(db);
			}
		}

	}

	/**
	 * 打开数据库
	 * 
	 * @return
	 * @throws SQLException
	 */
	public synchronized SQLiteDatabase open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return db;
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		DBHelper.close();
	}
	
	/**
	 * 根据APP_ID 查询对应的数据
	 * @param hashMap 
	 * @return
	 */
	public synchronized MACHINDATA inquiry(String M_ID){
		MACHINDATA data = null;
		try{db = open();}catch (Exception e) {e.printStackTrace();}
		Cursor cursor = db.query( 
	              DATABASE_TABLE, 
	              null, 
	              "M_ID" + "=='"
	      				+ M_ID+"'" , 
	              null, null, null,null);
		
		//		for(cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()){
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			data = new MACHINDATA();
			data.MAC = cursor.getString(cursor.getColumnIndex(MAC));
			data.IS_DELE = cursor.getInt(cursor.getColumnIndex(IS_DELE));
			data.MNAME = cursor.getString(cursor.getColumnIndex(MNAME));
			data.M_ID = cursor.getString(cursor.getColumnIndex("M_ID"));
			data.M_IP = cursor.getString(cursor.getColumnIndex("M_IP"));
			data.IS_UPDATA = cursor.getInt(cursor.getColumnIndex("IS_UPDATA"));
			
		}
		
		cursor.close();
		db.close();
		return data;
	}
	
	
	public synchronized MACHINDATA inquiryIP(String M_ID){
		MACHINDATA data = null;
		try{db = open();}catch (Exception e) {e.printStackTrace();}
		Cursor cursor = db.query( 
	              DATABASE_TABLE, 
	              null, 
	              "M_IP" + "=='"
	      				+ M_ID+"'" , 
	              null, null, null,null);
		
		//		for(cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()){
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			data = new MACHINDATA();
			data.MAC = cursor.getString(cursor.getColumnIndex(MAC));
			data.IS_DELE = cursor.getInt(cursor.getColumnIndex(IS_DELE));
			data.MNAME = cursor.getString(cursor.getColumnIndex(MNAME));
			data.M_ID = cursor.getString(cursor.getColumnIndex("M_ID"));
			data.M_IP = cursor.getString(cursor.getColumnIndex("M_IP"));
			data.IS_UPDATA = cursor.getInt(cursor.getColumnIndex("IS_UPDATA"));
		}
		
		cursor.close();
		db.close();
		return data;
	}
	
	
	public synchronized boolean inquiryIsExist(String app_id){
		try{db = open();}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Cursor cursor = db.query( 
	              DATABASE_TABLE, 
	              null, 
	              M_ID + "=='"
	      				+ app_id+"'" , 
	              null, null, null,null);
		boolean isExist = cursor.moveToNext();
		cursor.close();
		db.close();
		return isExist;
	}
	
	

	/**
	 * 向数据库中插入数据
	 * @throws DBNullException 
	 */

	public synchronized void insert(MACHINDATA data) throws DBNullException {
		isDBNullException(data);
		if(inquiryIsExist(data.MAC)){
			return;
		}
		Log.e("insert", "insert-------"+data);
		try{db = open();}catch (Exception e) {e.printStackTrace();}
		ContentValues initialValues = new ContentValues();
		initialValues.put(MAC, data.MAC);
		initialValues.put(IS_DELE, data.IS_DELE);
		initialValues.put(MNAME, data.MNAME);
		initialValues.put(M_ID, data.M_ID);
		initialValues.put(M_IP, data.M_IP);
		initialValues.put(IS_UPDATA, data.IS_UPDATA);
		db.insert(DATABASE_TABLE, null, initialValues);
		db.close();
	}
	


	/**
	 * 删除数据,
	 */
	public boolean delete(String aPP_ID) {
		try{db = open();}catch (Exception e) {}
		boolean b = db.delete(DATABASE_TABLE, M_ID + "== '"+ aPP_ID+"'", null) > 0;
		db.close();	
		return b;
	}

	

	
	
	/**
	 * 更新数据
	 * @param args
	 * @param aPP_STATE
	 * @return
	 */
	public synchronized boolean update(MACHINDATA data) {
		
		if(data == null){
			return false;
		}
		try{db = open();}catch (Exception e) {}
		ContentValues initialValues = new ContentValues();
		initialValues.put(IS_DELE, data.IS_DELE);
		initialValues.put(MNAME, data.MNAME);
		initialValues.put(M_ID, data.M_ID);
		
		initialValues.put(M_IP, data.M_IP);
		initialValues.put(MAC, data.MAC);
		initialValues.put(IS_UPDATA, data.IS_UPDATA);
		boolean bol = db.update(DATABASE_TABLE, initialValues, M_ID + "=='"
				+ data.M_ID+"'", null) > 0;
		db.close();		
		return true;
	}

	
	
	
	public Cursor getAll() {
		Cursor cur = db.query(DATABASE_TABLE, null, null, null, null, null,null);
		return cur;
	}
	
	public void isDBNullException(MACHINDATA data) throws DBNullException{
		if(data == null){
			throw new DBNullException("appinfo object is null");
		}
	}

	public static class MACHINDATA {
		public String MAC;
		public int IS_DELE;
		public String MNAME;
		public String M_ID;
		public String M_IP;
		public int IS_UPDATA;
	}

}
