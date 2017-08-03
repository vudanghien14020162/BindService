package com.example.hien.servicebindmedia;

/**
 * Created by hien on 8/3/17.
 */

public class ItemMusic {

    private String mNameSong;
    private String mNameArtist;
    private String mPath;

    public ItemMusic(String mNameSong, String mNameArtist, String mPath) {
        this.mNameSong = mNameSong;
        this.mNameArtist = mNameArtist;
        this.mPath = mPath;
    }

    public String getNameSong() {
        return mNameSong;
    }

    public String getNameArtist() {
        return mNameArtist;
    }

    public String getPath() {
        return mPath;
    }
}
