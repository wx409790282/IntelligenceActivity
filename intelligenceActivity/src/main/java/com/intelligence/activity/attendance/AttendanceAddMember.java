package com.intelligence.activity.attendance;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.LayoutRipple;
import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.data.User;
import com.intelligence.activity.http.HttpUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AttendanceAddMember extends BaseActivity {
    User user;
    EditText phoneEditText, nameEditText;
    ButtonRectangle confirmButtton;
    LayoutRipple addLayout;
    String name,phone="";
    String Tag="AttendanceAddMember";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_add_member);

        setTitle(this.getString(R.string.attendance_add_member));
        setLeftOnClick(null);
        setRigter(false, "");
        initView();

    }

    private void initView() {
        phoneEditText= (EditText) findViewById(R.id.attendance_search_phone);
        nameEditText= (EditText) findViewById(R.id.attendance_search_name);
        confirmButtton= (ButtonRectangle) findViewById(R.id.attendance_add_confirm);
        confirmButtton.setOnClickListener(addOnClickListener);
        addLayout= (LayoutRipple) findViewById(R.id.attendance_add_contact);
        addLayout.setOnClickListener(addOnClickListener);
        addLayout.setRippleSpeed(20);
        //addLayout.spee
    }
    private View.OnClickListener addOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          switch (v.getId()){
              case R.id.attendance_add_confirm:
                  Log.e(Tag, "add confirm");
                  if(phoneEditText.getText().toString().equals("") || phoneEditText.getText().toString().equals(null)){
                      Toast.makeText(AttendanceAddMember.this,AttendanceAddMember.this.getString(R.string.attendance_add_phone_error),Toast.LENGTH_SHORT).show();
                        break;
                  }
                  if(nameEditText.getText().toString().equals("") || nameEditText.getText().toString().equals(null)){
                      Toast.makeText(AttendanceAddMember.this,AttendanceAddMember.this.getString(R.string.attendance_add_name_error),Toast.LENGTH_SHORT).show();
                    break;
                  }
                  volley.add(new addMemberPostResquest(new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          try {
                              Log.e(Tag,response);
                              JSONObject resultJson=new JSONObject(response);
                              if(resultJson.getString("status").equals("1")){
                                  Toast.makeText(AttendanceAddMember.this,AttendanceAddMember.this.getString(R.string.attendance_add_member_success
                                  ),Toast.LENGTH_SHORT).show();
                              }else{
                                  Toast.makeText(AttendanceAddMember.this,resultJson.getString("status"),Toast.LENGTH_SHORT).show();
                              }

                          } catch (JSONException e) {
                              e.printStackTrace();
                              Toast.makeText(AttendanceAddMember.this,AttendanceAddMember.this.getString(R.string.attendance_add_member_error),Toast.LENGTH_SHORT).show();
                          }
                      }
                  },new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          error.printStackTrace();
                      }
                  }));
                  break;
              case R.id.attendance_add_contact:
                  startActivityForResult(new Intent(
                          Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
                  break;

          }
        }
    };

    @Override
    protected void onStart(){
        super.onStart();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            nameEditText.setText(name);
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phoneCursor.moveToNext()) {
                phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneEditText.setText(phone);
            }
            confirmButtton.performClick();
        }
    }

    class addMemberPostResquest extends StringRequest {
        public addMemberPostResquest(Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(Method.POST, HttpUrl.ADD_ATTENDANCE_MEMBER, listener, errorListener);
        }
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            //params.put("machineid", data.getMachineid());
            params.put("appid", HttpUrl.APP_ID);
            params.put("phonenumber", phoneEditText.getText().toString());
            params.put("username", nameEditText.getText().toString());
            return params;
        }
    }
}
