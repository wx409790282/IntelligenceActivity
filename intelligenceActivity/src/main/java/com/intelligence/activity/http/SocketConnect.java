package com.intelligence.activity.http;

import java.util.Vector;

import android.util.Log;
 
/**
 * 连接服务器线程类
 * 
 * @author Esa
 */
public class SocketConnect implements Runnable {
 
    private boolean isConnect = false;// 是否连接服务器
    private boolean isWrite = false;// 是否发送数据
    private static final Vector<byte[]> datas = new Vector<byte[]>();// 待发送数据队列
    private SocketBase mSocket;// socket连接
    private WriteRunnable writeRunnable;// 发送数据线程
    private String ip = null;
    private int port = -1;
 
    /**
     * 创建连接
     * 
     * @param callback
     *            回调接口
     */
    public SocketConnect(SocketCallback callback) {
        mSocket = new SocketBase(callback);// 创建socket连接
        writeRunnable = new WriteRunnable();// 创建发送线程
    }
 
    @Override
    public void run() {
        if (ip == null || port == -1) {
            throw new NullPointerException("not set address");
        }
        isConnect = true;
        while (isConnect) {
            synchronized (this) {
                try {
                    mSocket.connect(ip, port);// 连接服务器
                } catch (Exception e) {
                	e.printStackTrace();
                    try {
                        mSocket.disconnect();// 断开连接
                        Thread.sleep(6000);
                        continue;
                    } catch (InterruptedException e1) {
                        continue;
                    }
                }
            }
            try {
//            	while(!isWrite){
//            		Thread.sleep(1000);
//            	}
            	isWrite = true;
            	new Thread(writeRunnable).start();// 启动发送线程
            	Thread.sleep(2000);
                isConnect = false;
                mSocket.read();// 获取数据
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeRunnable.stop();
                mSocket.disconnect();
            }
        }
    }
 
    /**
     * 关闭服务器连接
     */
    public synchronized void disconnect() {
        isConnect = false;
        this.notify();
        resetConnect();
    }
 
    /**
     * 重置连接
     */
    public void resetConnect() {
    	if(writeRunnable != null)
    		writeRunnable.stop();// 发送停止信息
    	if(mSocket.isConnected())
    		mSocket.disconnect();
    }
 
    /**
     * 向发送线程写入发送数据
     */
    public void write(byte[] buffer) {
        writeRunnable.write(buffer);
    	Log.e("aa", new String(buffer));
//        writes(buffer);
//        isWrite = true;// 设置可发送数据
    }
 
    /**
     * 设置IP和端口
     * 
     * @param ip
     * @param port
     */
    public void setRemoteAddress(String host, int port) {
        this.ip = host;
        this.port = port;
    }
 
    /**
     * 发送数据
     */
    private boolean writes(byte[] buffer) {
        try {
            mSocket.write(buffer);
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
            resetConnect();
        }
        return false;
    }
 
    /**
     * 发送线程
     * 
     * @author Esa
     */
    private class WriteRunnable implements Runnable {
 
        @Override
        public void run() {
            while (isWrite) {
            	Log.e("aa", "开始写数据");
                synchronized (this) {
                    if (datas.size() <= 0) {
                        try {
                            this.wait();// 等待发送数据
                        } catch (InterruptedException e) {
                            continue;
                        }
                    }
                    while (datas.size() > 0) {
                        byte[] buffer = datas.remove(0);// 获取一条发送数据
                        if (isWrite) {
                            writes(buffer);// 发送数据
                        } else {
                            this.notify();
                        }
                    }
                    isWrite = false;
                }
                Log.e("aa", "数据写完了");
            }
        }
 
        /**
         * 添加数据到发送队列
         * 
         * @param buffer
         *            数据字节
         */
        public synchronized void write(byte[] buffer) {
            datas.add(buffer);// 将发送数据添加到发送队列
            this.notify();// 取消等待
        }
 
        public synchronized void stop() {
            isWrite = false;
            this.notify();
        }
    }
}
