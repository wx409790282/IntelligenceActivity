package com.intelligence.activity.attendance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.data.Machine;
import com.intelligence.activity.data.User;
import com.intelligence.activity.http.GsonGetRequest;
import com.intelligence.activity.http.HttpUrl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceMain extends BaseActivity {
    private Machine data;
    public LocationClient mLocationClient = null;

    JSONObject resultJsonObject=null;
    ArrayList<User> userlist=new ArrayList<>();
    Gallery gallery;
    TextView nameTextView,idTextView,worktimeTextView,phoneTextView;
    ImageView mainImageView;
    DisplayImageOptions options;
    MyLocationListener mMyLocationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_main);
//        userlist.add(new User("1", "123", "010201i.png"));
//        userlist.add(new User("2","14444","010201ui.png"));
//        userlist.add(new User("3","1555","010201bi.png"));
//        userlist.add(new User("4","破晓","010202i.png"));
//        userlist.add(new User("5","I am so happy today","010202ui.png"));
//        userlist.add(new User("6","来和我签下契约吧！","010202bi.png"));
        //init title
        setTitle(this.getString(R.string.attendance_group));
        setLeftOnClick(null);
        setRigter(true, R.drawable.add);//and i will set right
        setWifi(R.drawable.home, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AttendanceMain.this,AttendanceGroupSetting.class);
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });
        //get data from intent
        getData();
        initView();

        volley= Volley.newRequestQueue(getApplicationContext());
        //apply to server, get attendance member under this meachine
        volley.add(getUserArray(new Response.Listener<ArrayList<User>>() {

            @Override
            public void onResponse(ArrayList<User> response) {
                userlist = response;
                gallery.setAdapter(new AttendanceGalleryAdapter(AttendanceMain.this,userlist));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
        //volley.add(new StringRequest(Request.Method.GET,HttpUrl.GET_ATTENDANCE_LIST,))

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.portrait)
                .showImageOnFail(R.drawable.portrait3)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();



        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AttendanceMain.this, AttendanceHistory.class);
                intent.putExtra("user", userlist.get(position%userlist.size()));
                intent.putExtra("machine", data);
                startActivity(intent);
            }
        });
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(AttendanceMain.this, "item selected" + position%userlist.size(), Toast.LENGTH_SHORT).show();
                //mainImageView.setImageResource(((ImageView)view).getId());

                nameTextView.setText(userlist.get(position%userlist.size()).username);
                phoneTextView.setText(userlist.get(position%userlist.size()).phonenumber);
                ImageLoader.getInstance().displayImage(HttpUrl.IMGURL+userlist.get(position % userlist.size()).headurl, mainImageView,
                        options, null);
                //idTextView.setText(userlist.get(position).id);
                //nameTextView.setText(userlist.get(position).name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gallery.setUnselectedAlpha(0.3f);

    }

    private void initView() {
        gallery= (Gallery) findViewById(R.id.attendancemaingallery);
        mainImageView= (ImageView) findViewById(R.id.attendancemainImageView);
        nameTextView= (TextView) findViewById(R.id.attendance_member_name);
        phoneTextView= (TextView) findViewById(R.id.attendance_member_phone);
        idTextView= (TextView) findViewById(R.id.attendance_member_id);
        worktimeTextView= (TextView) findViewById(R.id.attendance_member_worktime);

    }

    //baseactivity, set right on click
    public void setRightOnClick(){
        Intent intent = new Intent(this,AttendanceAddMember.class);
        startActivity(intent);
    }
    //get data from main activity, including meachine id
    private void getData(){
        Intent intent = getIntent();
        if(intent != null){
            data = (Machine)intent.getSerializableExtra("data");
            if(data == null){
                data = new Machine();
                data.setMachineid(intent.getStringExtra("MACHINEID"));
            }
        }
        getLocationService();
    }

    //amazing function, combine gson and volley together
    public GsonGetRequest<ArrayList<User>> getUserArray(
                    Response.Listener<ArrayList<User>> listener,
                    Response.ErrorListener errorListener) {
        String url = HttpUrl.GET_ATTENDANCE_LIST;
        url+="/appid/"+HttpUrl.APP_ID;
        final Gson gson = new GsonBuilder().create();
        return new GsonGetRequest<>(
                        url,
                        new TypeToken<ArrayList<User>>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }



    private void getLocationService(){
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=3*1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        //option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        //option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //Toast.makeText(AttendanceHistory.this, "get location success", Toast.LENGTH_SHORT);
            StringBuffer sb = new StringBuffer(256);
            sb.append(location.getLatitude() + ":" + location.getLongitude());
            Log.e("service update",sb.toString());
            //String url=HttpUrl.PUT_ATTENDANCE_LOCATION;
            String url=HttpUrl.updatelocation;
            url+="/machineid/"+ data.getMachineid();
            url+="/appid/"+ HttpUrl.APP_ID;
            //url+="/distance/"+ 50;
            url+="/longitude/"+ location.getLongitude();
            url+="/latitude/"+ "" + location.getLatitude();
            url+="/type/"+ "1";

            //Log.e("get location success", "success" + location.getLatitude() + ":" + location.getLongitude());
            //sendRequestWithHttpClient(location.getLatitude(), location.getLongitude());
            volley.add(new LocationPostResquest(url,location.getLatitude(), location.getLongitude(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("AttendanceLocation",response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }));
        }
    }
    class LocationPostResquest extends StringRequest {
        double latitude;
        double longitude;
        public LocationPostResquest(String url,double l1,double l2,
                                    Response.Listener<String> listener, Response.ErrorListener errorListener) {

            super(Method.POST,url, listener, errorListener);
            Log.e("url",url);
            latitude=l1;
            longitude=l2;
        }
    }
}
