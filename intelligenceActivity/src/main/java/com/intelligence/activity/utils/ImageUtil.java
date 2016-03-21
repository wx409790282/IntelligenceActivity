package com.intelligence.activity.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 *
 * @author jiuhua.song
 */
public class ImageUtil {
	
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
		return newbmp;
	}

	
	/**
	 * 把drawable转成BITMAP
	 * @param drawable
	 * @return
	 */
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
