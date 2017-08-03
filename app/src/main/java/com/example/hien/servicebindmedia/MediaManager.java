package com.example.hien.servicebindmedia;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by hien on 8/3/17.
 */

public class MediaManager {

    private MediaPlayer mPlayer;

    public MediaManager() {
        mPlayer = new MediaPlayer();
    }

    public void init(String path) throws IOException {
        try {
            mPlayer.setDataSource(path);
        }catch (IllegalStateException e) {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(path);
        }

        mPlayer.prepare();
    }

    public void playMusic(){
        if(mPlayer != null && !mPlayer.isPlaying()){
            mPlayer.start();
        }
    }


    //giai phong
    public void releases() {
        mPlayer.release();
    }




}
