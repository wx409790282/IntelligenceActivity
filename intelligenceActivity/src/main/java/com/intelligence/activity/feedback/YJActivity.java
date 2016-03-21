package com.intelligence.activity.feedback;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class YJActivity extends BaseActivity {
	private EditText editText;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yj);

		editText = (EditText)findViewById(R.id.jy_id);

		setTitle(this.getString(R.string.feedback_main));
		setLeftOnClick(null);
		setRigter(true, this.getString(R.string.feedback_detail));
		setRightOnClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(YJActivity.this, YJListActivity.class);
				YJActivity.this.startActivity(intent);
			}
		});
		
		Button subitbtn = (Button) findViewById(R.id.subitbtn);
		subitbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
				list.add(new BasicNameValuePair("content",editText.getText().toString()));

				startHandler.setProcessing(false);
				HttpConnectionUtils httpUtil = new HttpConnectionUtils(startHandler);
				httpUtil.create(1, HttpUrl.FEEDBACK, list);
				httpUtil.setState(100);
			}
		});

	}


	private HttpHandler startHandler = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,state);
			JSONObject jsonObject = null;
			try {
				jsonObject =  new JSONObject(jObject);
				System.out.println(jsonObject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if (jsonObject.getString("status").equals("1")) {
					Dialog alertDialog = new AlertDialog.Builder(YJActivity.this).
							setTitle(YJActivity.this.getString(R.string.hint)).
							setMessage(YJActivity.this.getString(R.string.submitsuccess)).
							setPositiveButton(YJActivity.this.getString(R.string.confirm), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub 
								}
							}). 
							create();   
					alertDialog.show(); 
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		};

		protected void failed(String jObject,int state){
			super.failed(jObject,-1);
		}
	};
}
