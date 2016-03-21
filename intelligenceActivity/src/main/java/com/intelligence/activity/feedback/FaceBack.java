package com.intelligence.activity.feedback;

import org.json.JSONException;
import org.json.JSONObject;

public class FaceBack {
	public String createtime;//":"1413123754", //反馈的时间
	public String content;//":"qewfq", //反馈的内容
	public int type;//":"0", //0=app 发送到服务端,1=客服回复用户

	public void mapToHCustom(JSONObject jsonObject){
		try {
			createtime = jsonObject.getString("createtime");//干

			content = jsonObject.getString("content");//智能 小
			type = jsonObject.getInt("type");//智能 小
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
