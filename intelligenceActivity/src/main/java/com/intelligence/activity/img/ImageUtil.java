package com.intelligence.activity.img;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.StatFs;

public class ImageUtil {
	
	public static String ROOTPATH = Environment.getExternalStorageDirectory().getPath() 
		    + "/SH/";
	private static final String TAG = "ImageUtil";
	
	public static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 5 * 1024 * 1024;
	
	public static Bitmap getRoundedCornerBitmap(Drawable drawable) {
		//获取圆角图片     
		return getRoundedCornerBitmap(drawableToBitmap(drawable), 10.0f);  
	}
 
	// 将Drawable转化为Bitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	// 获得圆角图片的方法
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		if(!bitmap.isRecycled()){
			bitmap.recycle();
		}

		return output;
	}
	
	/**
	 * 计算sd卡的剩余空间
	 * @return
	 */
	public static int freeSpaceOnSd(){
		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFree = (double)statFs.getAvailableBlocks() * (double)statFs.getBlockSize() / 1024 / 1024;
		return (int)sdFree;
	}
	
	/**
	 * 修改文件最近使用时间
	 * @param path
	 * @param name
	 */
	public static void updateFileTime(String path, String name){
		File file = new File(path, name);
		long newTime = System.currentTimeMillis();
		file.setLastModified(newTime);
	}
	
	/**
	 * 保存图片到sd卡中
	 * @param bitmap
	 * @param urlPath
	 */
	public static void saveBitmapToSd(Bitmap bitmap, String urlPath){
		if(bitmap == null){
			return;
		}
		
		if(FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()){
			return; 
		}
		
		String fileName = convertUrlToFileName(urlPath);
		String dir = getDirection(fileName);
		
		File file = new File(dir + File.separator + fileName);
		
	}

	private static String getDirection(String fileName) {
		return null;
	}

	private static String convertUrlToFileName(String urlPath) {
		return null;
	}
	
	// ***BEGIN*** [翼优惠-图片加载] 王玉丰 2012-12-5 add
    /**
     * 
     * 将图片转化给byte[]操作
     * 
     * @param bm 图片对象
     * @return 图片byte[]
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        bytes = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return bytes;
    }
    // ***END***  [翼优惠-图片加载] 王玉丰 2012-12-5 add
    
    
    /**
	 * 图片的缩放
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidht = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
//		bitmap.recycle();
		return newbmp;
	}

	

	/**
	 * ID转化成BITMAP
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap drawableToBitmap(Context context,int resId){
		Drawable drawable = context.getResources().getDrawable(resId);
		return drawableToBitmap(drawable);
	}
	
	
	/**
	 * 图片本地保存</br> 1、图片存在不再进行存放 2、图片不存在进行存放
	 * 
	 * @param bitmap
	 * @throws IOException
	 */

	public static void saveImage(String imageName, Bitmap bmp) throws IOException {
		String path = ROOTPATH + "/images/" + imageName;
		File f = new File(path);
		if (!f.isFile())// 要存放的图片不存在
		{
			String dir = path.substring(0, path.lastIndexOf("/"));
			File dirFile = new File(dir);
			if (!dirFile.exists()) {// 目录不存在时，兴建
				boolean isMakeDir = dirFile.mkdirs();
				if (!isMakeDir) {// 兴建目录失败
					return;
				}
			}

			f.createNewFile();

			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(f);
				bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			} catch (FileNotFoundException e) {
			//	e.printStackTrace();
			} finally {
				if (fOut != null) {
					fOut.flush();
					fOut.close();
				}
			}
		}

	}

	/**
	 * 获取对应名称的图片
	 * 
	 * @param path
	 *            缓存的路径
	 * @return
	 */
	public static Bitmap getImage(String imageName) throws Exception {
		Bitmap bit = null;
		try {
			String path = ROOTPATH + "/images/" + imageName;
			File imageFile = new File(path);
			if (imageFile.exists()) {// 当前图片存在
				bit = BitmapFactory.decodeFile(path);
			}
		} catch (Exception e) {
		//	e.printStackTrace();
		//	throw new Exception();
		} 
		return bit;
	}
	
	/**
	 * 从安装包中读取图片
	 * 
	 * @param context
	 * @param imagePath
	 * @return
	 */
	public static Bitmap getImageFromLocal(Context context, String imageName)throws Exception {
		String imagePath="content/images/"+imageName;
		Bitmap bit = null;
		try {
			AssetManager assets = context.getAssets();
			InputStream is = assets.open(imagePath);
			bit = BitmapFactory.decodeStream(is);

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return bit;
	}

	// 获取SD卡上所有图片
	public List<Object> getImgFromSDcard() {
		List<Object> fileList = new ArrayList<Object>(); 
		String PATH = "/sdcard/";
		File file = new File("");
		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String filename = files[i].getName();
				// 获取bmp,jpg,png格式的图片
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".bmp")) {
					String filePath = files[i].getAbsolutePath();
					fileList.add(filePath);
				}
			} else if (files[i].isDirectory()) {
				PATH = files[i].getAbsolutePath();
				getImgFromSDcard();
			}
		}
		return fileList;
	}
}
