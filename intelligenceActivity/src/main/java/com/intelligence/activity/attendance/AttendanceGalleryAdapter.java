package com.intelligence.activity.attendance;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.intelligence.activity.R;
import com.intelligence.activity.data.User;
import com.intelligence.activity.http.HttpUrl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by wx091 on 2016/2/29.
 */
public class AttendanceGalleryAdapter extends BaseAdapter {
    //Item的修饰背景
    int mGalleryItemBackground;
    //上下文对象
    private Context mContext;
    ArrayList<User> users;
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.portrait)
            .showImageOnFail(R.drawable.portrait3)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new RoundedBitmapDisplayer(100))//set circle picture
            .build();
    //构造方法
    public AttendanceGalleryAdapter(Context c,ArrayList<User> users){
        mContext = c;
        this.users=users;
        //读取styleable资源
        TypedArray a = c.obtainStyledAttributes(R.styleable.AttendanceMain);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.AttendanceMain_android_galleryItemBackground, 0);
        a.recycle();

    }

    //返回项目数量
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //返回项目
    @Override
    public Object getItem(int position) {
        return position;
    }

    //返回项目Id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ImageView iv = new ImageView(mContext);
        //iv.setImageResource(users.get(position%users.size()).photoID);
        //给生成的ImageView设置Id，不设置的话Id都是-1
        iv.setId(position%users.size());
        iv.setLayoutParams(new Gallery.LayoutParams(200, 200));
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setBackgroundResource(mGalleryItemBackground);
        //iv.setAlpha(50);
        //iv.setScaleType(Sc);
        iv.setBackgroundColor(Color.alpha(1));
        ImageLoader.getInstance().displayImage(HttpUrl.IMGURL+users.get(position%users.size()).headurl,iv,
                options, null);
        return iv;
    }

}
