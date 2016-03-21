package com.intelligence.activity.controller;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UIController {
	private ControlThread _thread = new ControlThread();
	private static UIController _uiRunnable = null;
	/** 只执行一次的任务请求集 */
	private List<UICommand> _onceCommandList = new ArrayList<UICommand>();
	/** 循环执行的任务请求集 */
	private List<UICommand> _loopCommandList = new ArrayList<UICommand>();
	/** 循环执行任务间隔时间(ms) */
	public int _intervalTime = 50;
	private List<UIListner> lists = new ArrayList<UIController.UIListner>();
	public static interface UIListner{
		public void updata(UICommand cmd);
	}
	private UIController() {
		init();
	}

	public static UIController getInstance() {
		if (null == _uiRunnable)
			_uiRunnable = new UIController();
		return _uiRunnable;
	}

	/**
	 * 添加一次性任务
	 * 
	 * @param cmd
	 *            任务
	 */
	public synchronized void addOnceCommand(UICommand cmd) {
		this._onceCommandList.add(cmd);
	}

	/**
	 * 添加循环任务
	 * 
	 * @param cmd
	 *            任务
	 */
	public synchronized void addLoopCommand(UICommand cmd) {
		for (int i = 0 ; i < _loopCommandList.size(); i++) {
			UICommand uiCmd = this._loopCommandList.get(i);
			int cmdId = uiCmd._id;
			if (cmd._id == cmdId) {
				this._loopCommandList.remove(uiCmd);
			}
		}
		this._loopCommandList.add(cmd);
	}

	
	/**
	 * 添加监听器
	 * 
	 * @param cmd
	 *            任务
	 */
	public synchronized void addLoopCommand(UIListner listner) {
		this.lists.add(listner);
	}
	
	/**
	 * 删除循监听器
	 * 
	 * @param id
	 *            任务id
	 */
	public synchronized void removeLoopListner(UIListner listner) {
		lists.remove(listner);
	}
	
	/**
	 * 删除循环任务
	 * 
	 * @param id
	 *            任务id
	 */
	public synchronized void removeLoopCmd(int id) {
		int size = this._loopCommandList.size();

		for (int i = size - 1; i >= 0; i--) {
			UICommand uiCmd = this._loopCommandList.get(i);
			int cmdId = uiCmd._id;
			if (id == cmdId) {
				this._loopCommandList.remove(uiCmd);
				break;
			}
		}

	}

	private synchronized UICommand getCommand() {
		UICommand com = null;
		if (this._onceCommandList.size() > 0) {
			com = this._onceCommandList.get(0);
			this._onceCommandList.remove(com);
		}
		return com;
	}

	private void init() {
		this._thread = new ControlThread() {
			public void run() {
				while (this._isRun) {
					UICommand com = getCommand();
					if (com != null || _loopCommandList.size() > 0) {

						if (com != null) {
							executeOnceCmd(com);
						}

						if (_loopCommandList.size() > 0) {
							executeLoopCmd();
						}
						if(_CELLHandler != null){
							if(_beginToCurrentTime2 >= _period2){
								_beginToCurrentTime2 = 0;
								Log.e("aa", "_beginToCurrentTime2");
								_CELLHandler.sendEmptyMessage(01);
							}else{
								_beginToCurrentTime2 += _intervalTime;
							}
						}
						
						try {
							Thread.sleep(_intervalTime);// 处理完任务后，休息50毫秒
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						try {
							this._isSleep = true;
							Thread.sleep(400);// 无任务时，休息400毫秒
							this._isSleep = false;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			}
		};
		this._thread.start();
	}

	/**************************************************
	 * ********************定时器上传位置***************
	 **************************************************/
	public static final long _period2 = 1000*30 ;

	private long _beginToCurrentTime2 = 0;
	/** 主界面的句柄 */
	private Handler _CELLHandler = null;
	
	public void set_CELLHandler(Handler _CELLHandler) {
		if(this._CELLHandler != null)
			return;
		this._CELLHandler = _CELLHandler;
	}


	

	
	
	/**
	 * 执行一次性命令
	 * 
	 * @param cmd
	 */
	private void executeOnceCmd(UICommand cmd) {
		Message msg = Message.obtain();
		msg.obj = cmd;
		cmd._handler.sendMessage(msg);
	}

	/**
	 * 执行循环命令
	 * 
	 * @param cmd
	 */
	private void executeLoopCmd() {
		int size = this._loopCommandList.size();
		for (int i = 0; i < size; i++) {
			try {
				UICommand cmd = this._loopCommandList.get(i);
				if(cmd != null){
					Message msg = Message.obtain();
					msg.what = cmd._id;
					cmd._handler.sendMessage(msg);
					for(int j = 0 ; j < lists.size() ; j++){
						lists.get(j).updata(cmd);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	

	/**
	 * 销毁所有线程
	 */
	public void destroyAllThread() {
		this._thread._isRun = false;
		if(_CELLHandler != null){
			_CELLHandler = null;
		}
	}

}
