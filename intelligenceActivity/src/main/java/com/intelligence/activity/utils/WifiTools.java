package com.intelligence.activity.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.intelligence.activity.controller.FileHelper;
import com.intelligence.activity.db.DBHelper;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;

public class WifiTools {

	public static String ip = null;

	public static String humsavekey = "humsavekey";


	public static final String filepath = "ichome";

	public static void savewifilist(String msg,Context context){
		SharedPreferences preferences = context.getSharedPreferences("kettle", Context.MODE_PRIVATE);        

		Editor editor = preferences.edit();  
		//将登录标志位设置为false，下次登录时不在显示首次登录界面  
		editor.putString("wifilist", msg);  
		editor.commit();

	}

	public static JSONArray getwifilist(Context context){

		SharedPreferences preferences = context.getSharedPreferences("kettle", Context.MODE_PRIVATE);  
		String msg = preferences.getString("wifilist", null);

		if (msg != null) {
			try {
				return new JSONArray(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		return new JSONArray();
	}

	public static void updatawifilist(String wifiname,String wifipwd,Context context){

		JSONArray array = new JSONArray();
		SharedPreferences preferences = context.getSharedPreferences("kettle", Context.MODE_PRIVATE);  
		String msg = preferences.getString("wifilist", null);

		if (msg != null) {
			try {
				array = new JSONArray(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		boolean issame = false;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);

				if (jsonObject.getString("wifiname").equals(wifiname)) {
					jsonObject.put("wifipwd", wifipwd);
					issame = true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (issame == false) {
			JSONObject object = new JSONObject();
			try {
				object.put("wifiname", wifiname);
				object.put("wifipwd", wifipwd);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			array.put(object);
		}

		Editor editor = preferences.edit();  
		//将登录标志位设置为false，下次登录时不在显示首次登录界面  
		editor.putString("wifilist", array.toString());  
		editor.commit();

	}


	public static String getwifipwd(String wifiname,Context context){

		JSONArray array = new JSONArray();
		SharedPreferences preferences = context.getSharedPreferences("kettle", Context.MODE_PRIVATE);  
		String msg = preferences.getString("wifilist", null);

		if (msg != null) {
			try {
				array = new JSONArray(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		String wifipwd = "";
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);

				if (jsonObject.getString("wifiname").equals(wifiname)) {
					wifipwd = jsonObject.getString("wifipwd");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (wifipwd == null) {
			wifipwd = "";
		}

		return wifipwd;

	}


	/**
	 * 数据库
	 * @param context
	 * @return
	 */
	public static SQLiteDatabase db;

	public static SQLiteDatabase getDb(Context context){  

		DBHelper dbHelper = new DBHelper(context);//这段代码放到Activity类中才用this
		SQLiteDatabase db = null;
		db = dbHelper.getReadableDatabase();

		return db;
	}





	public static Bitmap small(Bitmap bitmap,float value) {
		Matrix matrix = new Matrix(); 
		matrix.postScale(value,value); //长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		return resizeBmp;
	}
	public static int dip2px(Context context, float dipValue){ 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int)(dipValue * scale + 0.5f); 
	} 

	public static int px2dip(Context context, float pxValue){ 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int)(pxValue / scale + 0.5f); 
	} 

	/**
	 * 图片合成
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createBitmap( Bitmap src, Bitmap watermark,int size,int type) {
		if( src == null ) {
			return null;
		}
		if (watermark.getWidth()<size) {
			for (int i = 2; i < 10; i++) {
				watermark = WifiTools.small(watermark, i);
				if (watermark.getWidth()>size) {
					break;
				}
			}
		}

		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		//create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap( size, size, Config.ARGB_8888 );//创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas( newb );
		//draw watermark into
		cv.drawBitmap( watermark, 0, 0, null );//在src的右下角画入水印
		//draw src into
		switch (type) {
		case 1:
			cv.drawBitmap( src, 0, 0, null );//在 0，0坐标开始画入src	
			break;
		case 2:
			cv.drawBitmap( src, size-w, 0, null );//在 0，0坐标开始画入src	
			break;
		case 3:
			cv.drawBitmap( src, 0, size-h, null );//在 0，0坐标开始画入src	
			break;
		case 4:
			cv.drawBitmap( src, size-w, size-h, null );//在 0，0坐标开始画入src	
			break;
		default:
			cv.drawBitmap( src, 0, 0, null );//在 0，0坐标开始画入src
			break;
		}

		//save all clip 
		cv.save( Canvas.ALL_SAVE_FLAG );//保存
		//store
		cv.restore();//存储

		newb = WifiTools.GetRoundedCornerBitmap(newb, size / 2);
		return newb;
	}

	//生成圆角图片
	public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap,int roundPx) {
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);					
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());		
			final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight()));
			//			final float roundPx = 14;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.BLACK);		
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));				

			final Rect src = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());

			canvas.drawBitmap(bitmap, src, rect, paint);	
			return output;
		} catch (Exception e) {			
			return bitmap;
		}
	}

	/**
	 * 二进制转十六进制
	 * @param s
	 * @return
	 */
	public static String BinaryToHex(String s){
		if (s.equals("")) {
			return "0";
		}
		return Long.toHexString(Long.parseLong(s,2));
	}

	/**
	 * 获取uuid
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		String uniqueId = uuid.toString();
		return uniqueId;

	}



	public static String weekname(String str){
		if (str.equals("0")) {
			return "永不";
		}else if(str.equals("1,2,3,4,5")){
			return "工作日";
		}else if(str.equals("1,2,3,4,5,6,7")){
			return "每天";
		}else{
			String[] wlist = str.split(",");
			String weekstr = "";

			for (String msg : wlist) {
				if (weekstr.equals("")) {
					weekstr = weekstr + WifiTools.weekOne(msg);
				}else{
					weekstr = weekstr + "," + WifiTools.weekOne(msg);
				}

			}
			return weekstr;
		}
	}
	public static String weekOne(String msg){
		if (msg.equals("1")) {
			return "周一";
		}else if (msg.equals("2")) {
			return "周二";
		}else if (msg.equals("3")) {
			return "周三";
		}else if (msg.equals("4")) {
			return "周四";
		}else if (msg.equals("5")) {
			return "周五";
		}else if (msg.equals("6")) {
			return "周六";
		}else if (msg.equals("7")) {
			return "周日";
		}
		return "";
	}






	/**
	 * Json 转成 Map<>
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getMapForJson(String jsonStr){
		JSONObject jsonObject ;
		try {
			jsonObject = new JSONObject(jsonStr);

			Iterator<String> keyIter= jsonObject.keys();
			String key;
			Object value ;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//			Log.e(HttpClientUtils.TAG, e.toString());
		}
		return null;
	}
	/**
	 * Json 转成 List<Map<>>
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String, Object>> getlistForJson(String jsonStr){
		List<Map<String, Object>> list = null;
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			JSONObject jsonObj ;
			list = new ArrayList<Map<String,Object>>();
			for(int i = 0 ; i < jsonArray.length() ; i ++){
				jsonObj = (JSONObject)jsonArray.get(i);
				list.add(getMapForJson(jsonObj.toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}


	public static Bitmap decodeUriAsBitmap(Uri uri,Context context){
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;

	}


	public static void saveuuid(String msg,Context context){
		SharedPreferences preferences = context.getSharedPreferences("kettle", Context.MODE_PRIVATE);        

		Editor editor = preferences.edit();  
		//将登录标志位设置为false，下次登录时不在显示首次登录界面  
		editor.putString("myuuid", msg);  
		editor.commit();

	}

	public static String getuuid(Context context){

		SharedPreferences preferences = context.getSharedPreferences("kettle", Context.MODE_PRIVATE);  
		String msg = preferences.getString("myuuid", null);
		return msg;
	}

	public static void savedevname(String name,String uuid,Context context){
		SharedPreferences preferences = context.getSharedPreferences("kettle", Context.MODE_PRIVATE);        

		if (name == null || name.equals("")) {
			String dev_type = uuid.substring(0, 2);
			if (dev_type.equals("01")) {
				name = "我的水壶";
			}else{
				name = "我的加湿器";
			}
		}

		Editor editor = preferences.edit();  
		//将登录标志位设置为false，下次登录时不在显示首次登录界面  
		editor.putString("devname"+uuid, name);  
		editor.commit();

	}

	public static String getdevname(String uuid,Context context){

		SharedPreferences preferences = context.getSharedPreferences("kettle", Context.MODE_PRIVATE);  
		String msg = preferences.getString("devname"+uuid, null);

		if (msg == null || msg.equals("")) {
			String dev_type = uuid.substring(0, 2);
			if (dev_type.equals("01")) {
				msg = "我的水壶";
			}else{
				msg = "我的加湿器";
			}
		}

		return msg;
	}


	public static void saveuuid(String msg){
		FileHelper fileHelper = new FileHelper();
		try {
			fileHelper.createSDFile(WifiTools.filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileHelper.writeSDFile(msg, WifiTools.filepath+"/msg.txt");
	}

	public static String readuuid(){
		
		String res = "";
		

		String filepath = Environment.getExternalStorageDirectory().getPath()+"/"+ WifiTools.filepath+"/msg.txt";
		File file = new File(filepath);

		if (file.exists()) {
			
			try {
				FileInputStream fin = new FileInputStream(filepath);
				// 用这个就不行了，必须用FileInputStream
				int length = fin.available();
				byte[] buffer = new byte[length];
				fin.read(buffer);
				res = EncodingUtils.getString(buffer, "UTF-8");////依Y.txt的编码类型选择合适的编码，如果不调整会乱码
				fin.close();//关闭资源
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Random random = new Random();
			int a = random.nextInt(800)+100;
			
			res = System.currentTimeMillis()+"";
			res = res+""+a;
			saveuuid(res);
		}
		
		return res;

	}
}
