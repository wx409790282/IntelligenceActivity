package com.intelligence.activity.utils;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by wx091 on 2016/3/2.
 */
public class MusicUtil {
    private Context c;
    private  MediaPlayer mp;
    public MusicUtil(Context c){
        this.c=c;
    }
    public  void playmp3(){
        mp = new MediaPlayer();
        try {
            mp.setDataSource( c.getAssets().openFd("btnmusic.mp3").getFileDescriptor());
            mp.prepare();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mp.start();
    }
}
