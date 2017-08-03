package com.example.hien.servicebindmedia;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hien on 8/3/17.
 */

public class ServiceBindMedia extends Service {


    private static final String TAG = ServiceBindMedia.class.getSimpleName();

    private List<ItemMusic> mListItem;
    private MediaManager mMedia;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "___onCreate!!!");

        mMedia = new MediaManager();

        mListItem = new ArrayList<>();

        getMusics();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG, "__IBinder");

        return new MyService(this);
    }

    public class MyService extends Binder{
        private ServiceBindMedia mService;

        public MyService(ServiceBindMedia mService) {
            this.mService = mService;
        }

        public ServiceBindMedia getService() {
            return mService;
        }
    }

    public void getMusics(){

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if(cursor == null){
            return;
        }

        int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String nameTitle = cursor.getString(title);
            String nameArtist = cursor.getString(artist);
            String namePath = cursor.getString(data);

            ItemMusic itemMusic = new ItemMusic(nameTitle, nameArtist, namePath);

            mListItem.add(itemMusic);

            cursor.moveToNext();
        }
    }

    public int getSizeList(){
        if(mListItem == null){
            return 0;
        }
        Log.d(TAG, "Size List: " + mListItem.size());

        return mListItem.size();
    }

    public ItemMusic getItemList(int position){
        if(mListItem == null){
            return null;
        }

        return mListItem.get(position);
    }

    public void playSong(int position) throws IOException {
        if(mListItem == null){
            return;
        }


        ItemMusic item = mListItem.get(position);

        String path = item.getPath();

        //giai phong cac music chay trc
        mMedia.releases();

        mMedia.init(path);

        mMedia.playMusic();

    }
}
