package com.intelligence.activity.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;

import com.intelligence.activity.R;
/**
 * 输入的弹出框
 * @author Administrator
 *
 */
public class EditDialog extends Dialog implements android.view.View.OnClickListener{

	private Context context;
	private Handler handler;
	
	private Button button;
	private EditText editview;
	InputMethodManager imm;
	private String txtString;
	public EditDialog(Context context,String txt) {
		// TODO 自动生成的构造函数存根
		super(context,R.style.MyDialogStyleBottom);
		// TODO 自动生成的构造函数存根
		this.context = context;
		this.txtString = txt;
		imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	public void setHandler(Handler handler){
		this.handler = handler;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_dialog);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		button = (Button)findViewById(R.id.comment_bt_id);
		button.setOnClickListener(this);
		
		editview = (EditText)findViewById(R.id.comment_edittext_id);
		editview.setText(txtString);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if(v.getId() == R.id.comment_bt_id){
			if(handler != null){
				Message msg = new Message();
				msg.what = 99;
				msg.obj = editview.getText();
				handler.sendMessage(msg);
			}
			imm.hideSoftInputFromWindow(editview.getWindowToken(), 0);
			dismiss();
		}
	}

}
