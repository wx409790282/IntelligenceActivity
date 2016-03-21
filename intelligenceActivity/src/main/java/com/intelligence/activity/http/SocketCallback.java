package com.intelligence.activity.http;

/**
 * 获取网络数据回调类
 * 
 * @author Esa
 * 
 */
public abstract interface SocketCallback {
     
    /**
     * 当建立连接是的回调
     */
    public abstract void connected();
 
    /**
     * 当获取网络数据回调接口
     * 
     * @param buffer
     *            字节数据
     */
    public abstract void receive(byte[] buffer);
 
    /**
     * 当断开连接的回调
     */
    public abstract void disconnect();
 
}
