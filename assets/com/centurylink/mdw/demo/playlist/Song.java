package com.centurylink.mdw.demo.playlist;

import org.json.JSONObject;

import com.centurylink.mdw.model.Jsonable;

public class Song implements Jsonable {
    
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    private String artist;
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    
    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }
    
    public Song(JSONObject json) {
        bind(json);
    }
}
