package com.intelligence.activity.db;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 自定义的数据管理的类
 * 
 * @author Administrator
 * 
 */
public class DBhelperManager {
	/**
	 * 数据库名
	 */
	private static final String DATABASE_NAME = "quickdial.db";

	/**
	 * 数据表名
	 */
	private static final String DATABASE_TABLE = "quickdial";

	/**
	 * 数据库版本
	 */
	private static final int DATABASE_VERSION = 1;

	/**
	 * 应用ID
	 */
	private static final String ZDY_ID                = "ZDY_ID";
	
	
	private static final String ZDY_NAME              = "ZDY_NAME";
	
	private static final String ZDY_ISDE              = "ZDY_ISDE";
	
	private static final String ZDY_SW                = "ZDY_SW";
	private static final String ZDY_TIME              = "ZDY_TIME";
	private static final String ZDY_TIME1             = "ZDY_TIME1";
	private static final String ZDY_ISOPEN            = "ZDY_ISOPEN";
	private static final String ZDY_ISZF              = "ZDY_ISZF";
	
	private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + 
	" (key_id integer default '1' not null primary key autoincrement, "
			+ "ZDY_ID text  not null, "
			+ "ZDY_NAME text  not null, "
			+ "ZDY_ISDE integer, "
			+ "ZDY_SW text not null," 
			+ "ZDY_TIME text not null," 
			+ "ZDY_TIME1 text not null," 
			+ "ZDY_ISZF text not null,"
			+ "ZDY_ISOPEN integer )" ;
	
//	"create table "+tb_phoneNumber+" (id integer default '1' not null primary key autoincrement,phoneNumber,addTime,type text  not null)";

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
	private static DBhelperManager instance;

	public DBhelperManager(final Context ctx) {
		context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	public static DBhelperManager getInstance(Context context) {
		if (instance == null)
			instance = new DBhelperManager(context);
		return instance;
	}

	public synchronized List<ZDYData> getAlermList(int state) {
		List<ZDYData> list = new ArrayList<DBhelperManager.ZDYData>();

		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = open();
			cursor = getAll();
			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				ZDYData data = new ZDYData();
				data.ZDY_ID = cursor.getString(cursor.getColumnIndex(ZDY_ID));
				data.ZDY_NAME = cursor.getString(cursor.getColumnIndex(ZDY_NAME));
				data.ZDY_ISDE = cursor.getInt(cursor.getColumnIndex(ZDY_ISDE));
				data.ZDY_SW = cursor.getString(cursor.getColumnIndex(ZDY_SW));
				data.ZDY_TIME = cursor.getString(cursor.getColumnIndex(ZDY_TIME));
				data.ZDY_TIME1 = cursor.getString(cursor.getColumnIndex(ZDY_TIME1));
				data.ZDY_ISOPEN = cursor.getInt(cursor.getColumnIndex(ZDY_ISOPEN));
				data.ZDY_ISZF = cursor.getString(cursor.getColumnIndex(ZDY_ISZF));
				if(state == 1){
					if(data.ZDY_ISOPEN == state)
					list.add(data);
				}else{
					list.add(data);
				}
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
	public synchronized ZDYData inquiry(String app_id){
		ZDYData data = null;
		try{db = open();}catch (Exception e) {e.printStackTrace();}
		Cursor cursor = db.query( 
	              DATABASE_TABLE, 
	              null, 
	              ZDY_ID + "=='"
	      				+ app_id+"'" , 
	              null, null, null,null);
		
		//		for(cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()){
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			data = new ZDYData();
			data.ZDY_ID = cursor.getString(cursor.getColumnIndex(ZDY_ID));
			data.ZDY_NAME = cursor.getString(cursor.getColumnIndex(ZDY_NAME));
			data.ZDY_ISDE = cursor.getInt(cursor.getColumnIndex(ZDY_ISDE));
			data.ZDY_SW = cursor.getString(cursor.getColumnIndex(ZDY_SW));
			data.ZDY_TIME = cursor.getString(cursor.getColumnIndex(ZDY_TIME));
			data.ZDY_TIME1 = cursor.getString(cursor.getColumnIndex(ZDY_TIME1));
			data.ZDY_ISOPEN = cursor.getInt(cursor.getColumnIndex(ZDY_ISOPEN));
			data.ZDY_ISZF = cursor.getString(cursor.getColumnIndex(ZDY_ISZF));
			
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
	              ZDY_ID + "=='"
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

	public synchronized void insert(ZDYData data) throws DBNullException {
		isDBNullException(data);
		if(inquiryIsExist(data.ZDY_ID)){
			return;
		}
		Log.e("insert", "insert-------"+data);
		try{db = open();}catch (Exception e) {e.printStackTrace();}
		ContentValues initialValues = new ContentValues();
		initialValues.put(ZDY_ID, data.ZDY_ID);
		initialValues.put(ZDY_NAME, data.ZDY_NAME);
		initialValues.put(ZDY_ISDE, data.ZDY_ISDE);
		initialValues.put(ZDY_SW, data.ZDY_SW);
		initialValues.put(ZDY_TIME, data.ZDY_TIME);
		initialValues.put(ZDY_TIME1, data.ZDY_TIME1);
		initialValues.put(ZDY_ISOPEN, data.ZDY_ISOPEN);
		initialValues.put(ZDY_ISZF, data.ZDY_ISZF);
		
		db.insert(DATABASE_TABLE, null, initialValues);
		db.close();
	}
	


	/**
	 * 删除数据,
	 */
	public boolean delete(String aPP_ID) {
		try{db = open();}catch (Exception e) {}
		boolean b = db.delete(DATABASE_TABLE, ZDY_ID + "== '"+ aPP_ID+"'", null) > 0;
		db.close();	
		return b;
	}

	

	
	
	/**
	 * 更新数据
	 * @param args
	 * @param aPP_STATE
	 * @return
	 */
	public synchronized boolean update(ZDYData data) {
		
		if(data == null){
			return false;
		}
		try{db = open();}catch (Exception e) {}
		ContentValues initialValues = new ContentValues();
		initialValues.put(ZDY_NAME, data.ZDY_NAME);
		initialValues.put(ZDY_ISOPEN, data.ZDY_ISOPEN);
		initialValues.put(ZDY_SW, data.ZDY_SW);
		initialValues.put(ZDY_TIME, data.ZDY_TIME);
		initialValues.put(ZDY_TIME1, data.ZDY_TIME1);
		initialValues.put(ZDY_ISZF, data.ZDY_ISZF);
		
		
		
		boolean bol = db.update(DATABASE_TABLE, initialValues, ZDY_ID + "=='"
				+ data.ZDY_ID+"'", null) > 0;
		db.close();		
		return true;
	}

	
	
	
	public Cursor getAll() {
		Cursor cur = db.query(DATABASE_TABLE, null, null, null, null, null,null);
		return cur;
	}
	
	public void isDBNullException(ZDYData data) throws DBNullException{
		if(data == null){
			throw new DBNullException("appinfo object is null");
		}
	}

	public static class ZDYData implements Serializable{
		/******自定义ID******/
		public String ZDY_ID;
		/******自定义名字******/
		public String ZDY_NAME;
		/******自定义是否可以删除******/
		public int ZDY_ISDE;
		/******自定义温度******/
		public String ZDY_SW;
		/******自定义保温时间******/
		public String ZDY_TIME;
		/******自定义净化******/
		public String ZDY_TIME1;
		/******自定是否打开******/
		public int ZDY_ISOPEN;
		/******自定义是否开启煮沸模式******/
		public String ZDY_ISZF;
		
	}

}
