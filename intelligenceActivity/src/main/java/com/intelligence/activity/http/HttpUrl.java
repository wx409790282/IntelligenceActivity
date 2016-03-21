package com.intelligence.activity.http;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpUrl {
	public static String APP_ID = null;

	//public static final String IMGURL = "http://api.sunsyi.com:8082/";
	public static final String IMGURL = "http://112.124.125.97:8082/";
	// public static final String URL = "http://112.124.125.97:8081";
	//
	//public static final String URL = "http://api.sunyie.com:8081";// 正式
	public static final String URL = "http://api.sunsyi.com:8081";//测试
	/**
	 * 注册APP
	 */
	public static final String REQ = URL + "/app/reg";
	/**
	 * 绑定商品
	 */
	public static final String BIND = URL + "/app/bind";
	/**
	 * 获取商品列表
	 */
	public static final String GET_MCHINELIST = URL + "/app/getmachinelist/";
	/**
	 * 删除商品
	 */
	public static final String DELE = URL + "/app/deletemachine/";

	/**
	 * 发送加热命令
	 */
	public static final String HEAT = URL + "/teapot/heat/";

	public static final String CANCELHEAT = URL + "/teapot/cancelheat/";

	public static final String SHTOPHEAT = URL + "/teapot/stopheat/";
	/**
	 * 获取设备的当前状态
	 */
	public static final String GET_STATE = URL + "/teapot/getstate/";
	/**
	 * 获取商品使用记录
	 */
	public static final String GETACTIONLOG = URL + "/teapot/getactionloglist/";

	public static final String STATE = URL + "/teapot/stat/";

	public static final String getnearmachine = URL + "/app/getnearmachine/";

	public static final String getorderlist = URL + "/teapot/getorderlist/";

	public static final String updatelocation = URL + "/app/updatelocation";

	/*
	 * 加湿器
	 */
	public static final String H_GET_STATE = URL + "/humidifier/getstate";
	// 开始停止
	public static final String H_START = URL + "/humidifier/start";
	public static final String H_STOP = URL + "/humidifier/stop";

	// 设置
	public static final String H_CONFIG = URL + "/humidifier/getconfig";

	public static final String H_SCONFIG = URL + "/humidifier/saveconfig";

	// 定时
	public static final String H_ORDER = URL + "/humidifier/order";

	public static final String H_CORDER = URL + "/humidifier/cancelorder";

	// 历史记录
	public static final String H_STAT = URL + "/humidifier/stat";

	public static final String H_STAT_LIST = URL
			+ "/humidifier/getactionloglist";

	// 意见反馈
	public static final String FEEDBACK = URL + "/app/feedback";

	public static final String FEEDBACK_LIST = URL + "/app/feedbackdetail";

	//this is attendance activity main
	public static final String GET_ATTENDANCE_LIST = URL + "/attendance/getPunchList";
	public static final String PUT_ATTENDANCE_LOCATION = URL + "/attendance/updatelocation";
	public static final String GET_MONTH_LIST = URL + "/attendance/getPunchMonth";
	public static final String GET_DAY_LIST = URL + "/attendance/getPunchDay";

	//this is attendance activity add member
	public static final String ADD_ATTENDANCE_MEMBER = URL + "/attendance/addpunch";

	//this is switch activity
	public static final String SWITCH_GET_LIGHT = URL + "/attendance/addpunch";
	public static final String SWITCH_UPDATE = URL + "/attendance/addpunch";
	public static final String SWITCH_RESPONSE = URL + "/attendance/addpunch";


}
