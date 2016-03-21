package com.intelligence.activity.http;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class HttpHandler extends Handler {

	private Context context;
	private ProgressDialog progressDialog;

	private boolean isProcessing;
	public void setProcessing(boolean isProcessing) {
		this.isProcessing = isProcessing;
	}


	public HttpHandler(Context context) {
		this.context = context;
	}

	
	protected void start() {
		if(!isProcessing){
			try {
				if(progressDialog != null && context == null)
					return;
				progressDialog = ProgressDialog.show(context,
						"Please Wait...", "processing...", true);
				progressDialog.setOnKeyListener(new OnKeyListener() {
					
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
						// TODO Auto-generated method stub
						if (keyCode == KeyEvent.KEYCODE_BACK&& event.getRepeatCount() == 0) {
							dialog.dismiss();
							return true;
						}
						return false;
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}

	protected void succeed(String jObject,int state) {
		if(progressDialog!=null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}

	protected void failed(String jObject,int state) {
		if(progressDialog!=null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}


	protected void connEerr() {

	}
	
	protected void otherHandleMessage(Message message){
		if(progressDialog!=null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
	
	public void handleMessage(Message message) {
		switch (message.what) {
		case HttpConnectionUtils.DID_START: //connection start
			start();
			break;
		case HttpConnectionUtils.DID_SUCCEED: //connection success
			if(progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
			
			
			try {
				String response = (String) message.obj;
				Log.e(context.getClass().getSimpleName(), "http connection return."
						+ response);
				JSONObject jObject = new JSONObject(response == null ? ""
						: response.trim());
				if ("1".equals(jObject.getString("status"))) { //operate success
					succeed(response,message.arg1);
				} else {
					failed(response,message.arg1);
				}
			} catch (JSONException e1) {
				if(progressDialog!=null && progressDialog.isShowing()){
					progressDialog.dismiss();
				}
				e1.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
				if(progressDialog!=null && progressDialog.isShowing()){
					progressDialog.dismiss();
				}
			}
			break;
		case HttpConnectionUtils.DID_ERROR: //connection error
			try{
				if(progressDialog!=null && progressDialog.isShowing()){
					progressDialog.dismiss();
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			Exception e = (Exception) message.obj;
			e.printStackTrace();
			Log.e(context.getClass().getSimpleName(), "connection fail."
					+ e.getMessage());
			connEerr();
			break;
		default:
			otherHandleMessage(message);
				break;
		}
		
		
	}

}