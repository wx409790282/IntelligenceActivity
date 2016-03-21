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
import android.text.TextUtils;
import android.util.Log;

/**
 * 闹钟相关的数据管理类
 * 
 * @author Administrator
 * 
 */
public class NZ_DBhelperManager {
	/**
	 * 数据库名
	 */
	private static final String DATABASE_NAME = "nz_quickdial.db";

	/**
	 * 数据表名
	 */
	private static final String DATABASE_TABLE = "nz_quickdial";

	/**
	 * 数据库版本
	 */
	private static final int DATABASE_VERSION = 3;

	/**
	 * 应用ID
	 */
	private static final String NZ_ID = "ZDY_ID";

	private static final String NZ_NAME = "ZDY_NAME";

	private static final String ZDY_ISDE = "ZDY_ISDE";

	private static final String ZDY_SW = "ZDY_SW";
	private static final String ZDY_TIME = "ZDY_TIME";
	private static final String ZDY_TIME1 = "ZDY_TIME1";
	private static final String ZDY_ISOPEN = "ZDY_ISOPEN";

	private static final String NZ_ZDYID = "NZ_ZDYID";

	private static final String NZ_START = "NZ_START";
	private static final String NZ_END = "NZ_END";
	private static final String NZ_LEVER = "NZ_LEVER";

	private static final String ORDER_ID = "ORDER_ID";

	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE
			+ " (key_id integer default '1' not null primary key autoincrement, "
			+ "ZDY_ID text  not null, " + "ZDY_NAME text  not null, "
			+ "ZDY_ISDE integer, " + "NZ_LEVER text  not null, "
			+ "NZ_ZDYID text  not null, " + "NZ_START integer, "
			+ "NZ_END integer, " + "ZDY_SW text not null,"
			+ "ZDY_TIME text not null," + "ZDY_TIME1 text not null,"
			+ "ORDER_ID text," + "ZDY_ISOPEN integer )";

	// "create table "+tb_phoneNumber+" (id integer default '1' not null primary key autoincrement,phoneNumber,addTime,type text  not null)";

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
	private static NZ_DBhelperManager instance;

	public NZ_DBhelperManager(final Context ctx) {
		context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	public static NZ_DBhelperManager getInstance(Context context) {
		if (instance == null)
			instance = new NZ_DBhelperManager(context);
		return instance;
	}

	public synchronized List<NZYData> getAlermList() {
		List<NZYData> list = new ArrayList<NZ_DBhelperManager.NZYData>();

		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = open();
			cursor = getAll();
			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				NZYData data = new NZYData();
				data.NZ_ID = cursor.getString(cursor.getColumnIndex(NZ_ID));
				data.NZ_NAME = cursor.getString(cursor.getColumnIndex(NZ_NAME));
				data.NZ_TX1 = cursor.getInt(cursor.getColumnIndex(ZDY_ISDE));
				data.NZ_SW = cursor.getString(cursor.getColumnIndex(ZDY_SW));
				data.NZ_TIME = cursor
						.getString(cursor.getColumnIndex(ZDY_TIME));
				data.NZ_MS = cursor.getString(cursor.getColumnIndex(ZDY_TIME1));
				data.NZ_ISOPEN = cursor.getInt(cursor
						.getColumnIndex(ZDY_ISOPEN));
				data.NZ_ZDYID = cursor.getString(cursor
						.getColumnIndex(NZ_ZDYID));
				data.NZ_LELVER = cursor.getString(cursor
						.getColumnIndex(NZ_LEVER));
				data.NZ_START = cursor.getInt(cursor.getColumnIndex(NZ_START));
				data.NZ_END = cursor.getInt(cursor.getColumnIndex(NZ_END));
				data.ORDER_ID = cursor.getString(cursor
						.getColumnIndex(ORDER_ID));

				list.add(data);
			}
		} catch (Exception e) {
			// TODO: handle exceptionn
			e.printStackTrace();
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
			if (oldVersion != newVersion) {
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
	 * 
	 * @param hashMap
	 * @return
	 */
	public synchronized NZYData inquiry(String app_id) {
		NZYData data = null;
		try {
			db = open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Cursor cursor = db.query(DATABASE_TABLE, null, NZ_ID + "=='" + app_id
				+ "'", null, null, null, null);

		// for(cursor.moveToLast(); ! cursor.isBeforeFirst();
		// cursor.moveToPrevious()){
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			data = new NZYData();
			data.NZ_NAME = cursor.getString(cursor.getColumnIndex(NZ_NAME));

		}

		cursor.close();
		db.close();
		return data;
	}

	public synchronized boolean inquiryIsExist(String app_id) {
		try {
			db = open();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Cursor cursor = db.query(DATABASE_TABLE, null, NZ_ID + "=='" + app_id
				+ "'", null, null, null, null);
		boolean isExist = cursor.moveToNext();
		cursor.close();
		db.close();
		return isExist;
	}

	/**
	 * 向数据库中插入数据
	 * 
	 * @throws DBNullException
	 */

	public synchronized void insert(NZYData data) throws DBNullException {
		isDBNullException(data);
		if (inquiryIsExist(data.NZ_ID)) {
			return;
		}
		Log.e("aa", "insert-------" + data);
		try {
			db = open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ContentValues initialValues = new ContentValues();
		initialValues.put(NZ_ID, data.NZ_ID);
		initialValues.put(NZ_NAME, data.NZ_NAME);
		initialValues.put(ZDY_ISDE, data.NZ_TX1);
		initialValues.put(ZDY_SW, data.NZ_SW);
		initialValues.put(ZDY_TIME, data.NZ_TIME);
		initialValues.put(ZDY_TIME1, data.NZ_MS);
		initialValues.put(ZDY_ISOPEN, data.NZ_ISOPEN);
		initialValues.put(NZ_END, data.NZ_END);
		initialValues.put(NZ_LEVER, data.NZ_LELVER);
		initialValues.put(NZ_START, data.NZ_START);
		initialValues.put(NZ_ZDYID, data.NZ_ZDYID);
		initialValues.put(NZ_ZDYID, data.NZ_ZDYID);
		db.insert(DATABASE_TABLE, null, initialValues);
		db.close();
	}

	/**
	 * 删除数据,
	 */
	public boolean delete(String aPP_ID) {
		try {
			db = open();
		} catch (Exception e) {
		}
		boolean b = db.delete(DATABASE_TABLE, NZ_ID + "== '" + aPP_ID + "'",
				null) > 0;
		db.close();
		return b;
	}

	/**
	 * 更新加热后的OrederId
	 * 
	 * @param args
	 * @param aPP_STATE
	 * @return
	 */
	public synchronized boolean updateOrederId(String order_id, String nz_id) {
		if (TextUtils.isEmpty(order_id)) {
			return false;
		}
		try {
			db = open();
		} catch (Exception e) {
		}
		ContentValues initialValues = new ContentValues();
		initialValues.put(ORDER_ID, order_id);
		boolean bol = db.update(DATABASE_TABLE, initialValues, NZ_ID + "=='"
				+ nz_id + "'", null) > 0;
		db.close();
		return true;

	}

	// /**
	// * 更新数据
	// *
	// * @param args
	// * @param aPP_STATE
	// * @return
	// */
	// public synchronized boolean updateNZ_SW(String NZ_SW, String nz_id) {
	// if (TextUtils.isEmpty(NZ_SW) || TextUtils.isEmpty(NZ_ID)) {
	// return false;
	// }
	// try {
	// db = open();
	// } catch (Exception e) {
	// }
	// ContentValues initialValues = new ContentValues();
	// initialValues.put(ZDY_SW, NZ_SW);
	// boolean bol = db.update(DATABASE_TABLE, initialValues, NZ_ID + "=='"
	// + nz_id + "'", null) > 0;
	// db.close();
	// return true;
	//
	// }

	/**
	 * 更新数据
	 * 
	 * @param args
	 * @param aPP_STATE
	 * @return
	 */
	public synchronized boolean update(NZYData data) {

		if (data == null) {
			return false;
		}
		try {
			db = open();
		} catch (Exception e) {
		}
		ContentValues initialValues = new ContentValues();
		// initialValues.put(NZ_NAME, data.NZ_NAME);
		// initialValues.put(ZDY_ISOPEN, data.NZ_ISOPEN);
		// initialValues.put(ZDY_SW, data.NZ_SW);
		// initialValues.put(ZDY_TIME, data.NZ_TIME);
		// initialValues.put(ZDY_TIME1, data.NZ_MS);
		//
		//
		// initialValues.put(NZ_START, data.NZ_START);
		// initialValues.put(NZ_LEVER, data.NZ_LELVER);
		// initialValues.put(NZ_START, data.NZ_START);
		// initialValues.put(NZ_ZDYID, data.NZ_ZDYID);
		//
		//
		// initialValues.put(NZ_ID, data.NZ_ID);
		Log.e("aa", data.NZ_SW);
		initialValues.put(NZ_NAME, data.NZ_NAME);
		initialValues.put(ZDY_ISDE, data.NZ_TX1);
		initialValues.put(ZDY_SW, data.NZ_SW);
		initialValues.put(ZDY_TIME, data.NZ_TIME);
		initialValues.put(ZDY_TIME1, data.NZ_MS);
		initialValues.put(ZDY_ISOPEN, data.NZ_ISOPEN);
		initialValues.put(NZ_END, data.NZ_END);
		initialValues.put(NZ_LEVER, data.NZ_LELVER);
		initialValues.put(NZ_START, data.NZ_START);
		// initialValues.put(ORDER_ID, "order_id");
		boolean bol = db.update(DATABASE_TABLE, initialValues, NZ_ID + "=='"
				+ data.NZ_ID + "'", null) > 0;
		db.close();
		return true;
	}

	public Cursor getAll() {
		Cursor cur = db.query(DATABASE_TABLE, null, null, null, null, null,
				null);
		return cur;
	}

	public void isDBNullException(NZYData data) throws DBNullException {
		if (data == null) {
			throw new DBNullException("appinfo object is null");
		}
	}

	public static class NZYData implements Serializable {
		public String NZ_ID;
		public String NZ_NAME;
		public int NZ_TX1;
		public String NZ_SW;
		public String NZ_TIME;
		public String NZ_MS;
		public int NZ_ISOPEN;
		public int NZ_START=1;//0是打开，1是关闭，默认关闭
		public int NZ_END=1;//0是打开，1是关闭，默认关闭
		public int NZ_LELVER_OPEN;
		public String NZ_LELVER;
		public String NZ_ZDYID;
		public String ORDER_ID;
	}

}
