package com.intelligence.activity.view;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

import com.intelligence.activity.R;

/**
 * 对话框提
 * jiuhua.song
 */
public class PromptDialog {

	/**
	 * 关闭对话框时调用接口
	 *  @author jiuhua.song
	 */
	public interface DialogListener {
		/**
		 * 确定按钮时回调
		 * @param flag  
		 */
		public void doComfirm(int flag);
		/**
		 * 取消按钮回调
		 * @param flag
		 */
		public void doCancel(int flag);
		
		/**
		 * back回调
		 * @param flag
		 */
		public void doBack(int flag);
		
	}
	/**
	 *确定对话框并回调涵数
	 * @param context
	 * @param showStr
	 * @param DialogListener
	 * @param flag
	 */
	public static void show(final Context context,final String showStr, final DialogListener dialogListener)
	{
		show(context, showStr, dialogListener,-1);
	}
	
	
	/**
	 *确定对话框并回调涵数
	 * @param context
	 * @param showStr
	 * @param dialogListener
	 * @param flag
	 */
	public static void show(final Context context,final String showStr, final DialogListener dialogListener,final int flag)
	{
		try{
			final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle(context.getString(R.string.dialog_title));
			dialog.setMessage(showStr);	
			dialog.setPositiveButton(context.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					if(dialogListener!=null){
						dialogListener.doComfirm(flag);
					}
				}
			});
			dialog.show();
			
			dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					if (keyCode == KeyEvent.KEYCODE_BACK&& event.getRepeatCount() == 0) {
						dialog.dismiss();
						if(dialogListener != null){
							dialogListener.doBack(flag);
						}
						return true;
					}
					return false;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 确定、取消对话框并回调函
	 * @param context
	 * @param showStr
	 * @param dialogListener
	 * @param flag
	 */
	public static void show(final Context context,final String showStr,final DialogListener dialogListener, final boolean isCancel)
	{
		show(context, showStr, dialogListener, -1, isCancel);
	}
	
	/**
	 * 确定、取消对话框并回调函
	 * @param context
	 * @param showStr
	 * @param DialogListener
	 * @param flag
	 */
	public  static void show(final Context context,final String showStr, final DialogListener dialogListener, final int flag, final boolean isCancel)
	{
		final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(context.getString(R.string.dialog_title));
		dialog.setMessage(showStr);	
		dialog.setPositiveButton(context.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				if(dialogListener!=null){
					dialogListener.doComfirm(flag);
				}
			}
		});
		dialog.setNegativeButton(context.getString(R.string.btn_cancel),new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				if(dialogListener!=null){
					if(isCancel){
						dialogListener.doCancel(flag);
					}
				}
			}

		});
		dialog.show();
		
		dialog.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK&& event.getRepeatCount() == 0) {
					dialog.dismiss();
					if(dialogListener != null){
						dialogListener.doBack(flag);
					}
					return true;
				}
				return false;
			}
		});
	}
}
