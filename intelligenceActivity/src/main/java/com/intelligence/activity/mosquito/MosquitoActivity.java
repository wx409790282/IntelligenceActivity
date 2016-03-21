package com.intelligence.activity.mosquito;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.gc.materialdesign.views.ButtonIcon;
import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;

public class MosquitoActivity extends BaseActivity {

    ButtonIcon clockbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosquito);

        setTitle(this.getString(R.string.mosquito));
        setLeftOnClick(null);
        setRigter(false, 0);

        clockbtn= (ButtonIcon) findViewById(R.id.mosquitoClock);
        ImageView clockImg=new ImageView(this);
        clockImg.setImageResource(R.drawable.roundclock);
        //clockbtn.setIcon(clockImg);
        clockbtn.setDrawableIcon(MosquitoActivity.this.getResources().getDrawable(R.drawable.roundclock));
    }
}
