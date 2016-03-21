/*
 * 文件名：FileUtil.java
 * 创建人：Administrator
 * 创建时间：2012-12-5
 * 版     权：Copyright Easier Digital Tech. Co. Ltd. All Rights Reserved.
 */
package com.intelligence.activity.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileLock;


/**
 * File通用操作工具类
 * 
 * @author 王玉丰
 * @version [HicdmaCoupon2, 2012-12-5]
 */
public class FileUtil {
    /**
     * FileUtil TAG
     */
    private static final String TAG = "FileUtil";
    
    /**
     * 文件转化byte[]操作
     * @param fileName 文件路径
     * @return 文件的byte[]格式
     */
    public static byte[] fileToByte(String fileName) {
        try {
            return fileToByte(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 文件转化byte[]操作
     * 
     * @param file 需要转化为byte[]的文件
     * @return 文件的byte[]格式
     * @throws IOException IO流异常
     */
    public static byte[] fileToByte(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] barr = new byte[1024];
            while (true) {
                int r = in.read(barr);
                if (r <= 0) {
                    break;
                }
                buffer.write(barr, 0, r);
            }
            return buffer.toByteArray();
        } finally {
            closeStream(in);
        }
    }
    
    /**
     * 将文件的byte[]格式转化成一个文件
     * 
     * @param b 文件的byte[]格式
     * @param fileName 文件名称
     * @return 转化后的文件
     */
    public static File byteToFile(byte[] b, String fileName) {
        BufferedOutputStream bos = null;
        File file = null;
        // 增加文件锁处理
        FileLock fileLock = null;
        try {
            file = new File(fileName);
            if (!file.exists()) {
                File parent = file.getParentFile();
                // 此处增加判断parent != null && !parent.exists()
                if (parent != null && !parent.exists() && !parent.mkdirs()) {
                    // 创建不成功的话，直接返回null
                    return null;
                }
            }
            FileOutputStream fos = new FileOutputStream(file);
            // 获取文件锁
            fileLock = fos.getChannel().tryLock();
            if (fileLock != null) {
                bos = new BufferedOutputStream(fos);
                bos.write(b);
            }
        } catch (IOException e) {
        } finally {
            if (fileLock != null) {
                try {
                    fileLock.release();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // ***END***  [ST2-图片读取管理] 王玉丰 2012-8-6 modify
            closeStream(bos);
        }
        return file;
    }

    /**
     * 专门用来关闭可关闭的流
     * 
     * @param beCloseStream 需要关闭的流
     * @return 已经为空或者关闭成功返回true，否则返回false
     */
    public static boolean closeStream(java.io.Closeable beCloseStream) {
        if (beCloseStream != null) {
            try {
                beCloseStream.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
