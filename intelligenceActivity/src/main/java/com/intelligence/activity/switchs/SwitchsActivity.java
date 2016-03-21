package com.intelligence.activity.switchs;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.Switch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.data.Constants;
import com.intelligence.activity.data.Machine;
import com.intelligence.activity.http.GsonGetRequest;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.utils.WifiTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SwitchsActivity extends BaseActivity {

    Machine data;
    TextView nameTV,idTv;
    ButtonRectangle searchButton;
    ListView switchLV;
    ArrayList<LightWithConnectState> lightList=new ArrayList<>();
    String Tag="SwitchsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchs);

        setTitle(this.getString(R.string.switch_name));
        setLeftOnClick(null);
        setRigter(true,this.getString(R.string.save));
        //setRigter(null);//and i will set right
        initView();
        //getData();
        data=new Machine("10000","switch","1");
        lightList.add(new LightWithConnectState(new Machine("10","l","1")));
        lightList.add(new LightWithConnectState(new Machine("fg","light","1")));
        lightList.add(new LightWithConnectState(new Machine("dfdf","duke","hie")));
        switchLV.setAdapter(new SwitchListViewAdapter());

        volley.add(getLightList(new Response.Listener<ArrayList<Machine>>() {
            @Override
            public void onResponse(ArrayList<Machine> response) {
                for (Machine tempc:response) {
                    for(LightWithConnectState c:lightList){
                        if(tempc.getMachineid().equals(c.m.getMachineid())){
                            c.isConnected=true;
                        }
                    }
                }
                switchLV.getAdapter().notify();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));

    }

    private void initView() {
        nameTV= (TextView) findViewById(R.id.switch_name);
        idTv= (TextView) findViewById(R.id.switch_id);
        searchButton= (ButtonRectangle) findViewById(R.id.switch_search);
        switchLV= (ListView) findViewById(R.id.switch_listview);
    }
    //get data from main activity ,include switch and lights.then init lightlist by lights. we will
    //update lights state after get response from server.
    //also we need to skan dababase to get tag/label/name of each meachine
    private void getData(){
        Intent intent = getIntent();
        if(intent != null){
            data = (Machine)intent.getSerializableExtra("data");
            data.type= Constants.machineType.switchs;
            data.setName(WifiTools.getdevname(data.getMachineid(), this));
            if(data == null){
                data = new Machine();
                data.setMachineid(intent.getStringExtra("MACHINEID"));
            }
            ArrayList<Machine> machineslist= (ArrayList<Machine>) intent.getSerializableExtra("lightlist");
            for (Machine c:machineslist) {
                if(c.getMachineid().startsWith("03")){
                    LightWithConnectState l=new LightWithConnectState(c);
                    l.m.setName(WifiTools.getdevname(l.m.getMachineid(), this));
                    lightList.add(l);
                }
            }
        }
        //after init data and lightlist,we may update textview in main view
        nameTV.setText(data.getName());
        idTv.setText(data.getMachineid());
    }

    private class SwitchListViewAdapter extends BaseAdapter {
        LayoutInflater inf = LayoutInflater.from(SwitchsActivity.this);
        @Override
        public int getCount() {
            return lightList.size();
        }

        @Override
        public Object getItem(int position) {
            return lightList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            final int p=position;
            if (convertView == null) {
                holder = new Holder();
                convertView = inf.inflate(R.layout.switchs_listview_item, null);
                holder.name = (TextView)convertView.findViewById(R.id.switch_listview_item_name);
                holder.s= (Switch) convertView.findViewById(R.id.switch_listview_item_switch);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            Log.e(Tag, "" + position);
            holder.name.setText(lightList.get(position).m.getName());
            holder.s.setChecked(lightList.get(position).isConnected);
            holder.s.setOncheckListener(new Switch.OnCheckListener() {
                @Override
                public void onCheck(Switch view, boolean check) {
                    lightList.get(p).isConnected=check;
                }
            });
            return convertView;
        }
        class Holder {
            TextView name;
            Switch s;
        }
    }
    //new a class of machine contains a bool stand for connnect state;
    private class LightWithConnectState{
        boolean isConnected=false;
        Machine m;
        public LightWithConnectState(Machine machine){
            this.m=machine;
        }
    }

    //gson get request, with this we can get lights which is connect to this switch
    public GsonGetRequest<ArrayList<Machine>> getLightList( Response.Listener<ArrayList<Machine>> listener,
            Response.ErrorListener errorListener) {
        String url = HttpUrl.GET_ATTENDANCE_LIST;
        url+="/appid/"+ HttpUrl.APP_ID;
        url+="/machineid/"+ data.getMachineid();
        final Gson gson = new GsonBuilder().create();
        return new GsonGetRequest<>(
                url,
                new TypeToken<ArrayList<Machine>>() {}.getType(),
                gson,
                listener,
                errorListener
        );
    }
    //gson post, with this we can change state of lights,conn or diaconn it from switch
    //but i am not sure should i update state one by one, or add a save button and update together

    //gson get request,we need to response to click of switch meachine.when someone press this device,we need to
    //show response on view, so that customs will know ,en ,this is switch in my bedroom
    class LightUpdatePostResquest extends StringRequest {
        public LightUpdatePostResquest( Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(Method.POST,HttpUrl.SWITCH_UPDATE, listener, errorListener);
        }
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("machineid", data.getMachineid());
            params.put("appid", HttpUrl.APP_ID);
            return params;
        }
    }
    //amazing function, combine gson and volley together

}
