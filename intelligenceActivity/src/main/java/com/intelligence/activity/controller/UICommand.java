package com.intelligence.activity.controller;

import android.os.Handler;

public class UICommand {
	/** Handler */
	public Handler _handler = null;
	/** 命令Id，用于需要循环执行的小控件 */
	public int _id = -1;

	public UICommand(Handler handler, int id) {
		this._handler = handler;
		this._id = id;
	}

	public UICommand(Handler handler) {
		this._handler = handler;
	}
}
