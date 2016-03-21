package com.intelligence.activity.humidifier;


import com.intelligence.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class MarkActivity extends Activity {

	private EditText editText;
	HTiming timing;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mark);
		
		timing =  (HTiming)getIntent().getSerializableExtra("timing");

		NavigationBar bar = (NavigationBar) findViewById(R.id.navigationbar);
		editText = (EditText) findViewById(R.id.edittext);

		
		editText.setText(timing.title);


		bar.showLeftBtn(true);
		bar.setLeftOnClickListener(new MyOnClickListener() {  
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				timing.title = editText.getText().toString();
				Intent it = new Intent();
		        Bundle b2 = new Bundle();
		        b2.putSerializable("timing", timing);
		        it.putExtras(b2);
		        setResult(RESULT_OK,it);
		        
				finish();       
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.mark, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
