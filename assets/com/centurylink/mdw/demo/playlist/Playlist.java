package com.centurylink.mdw.demo.playlist;

import org.json.JSONArray;
import org.json.JSONObject;

import com.centurylink.mdw.model.Jsonable;

/**
 * Playlist model. 
 */
public class Playlist implements Jsonable {
    
    private JSONArray songs;
    public JSONArray getSongs() { return songs; }
    public void setSongs(JSONArray songs) { this.songs = songs; }
    
    public Playlist(JSONObject json) {
        bind(json);
    }
    
    public void addSong(String title, String artist) {
        Song song = new Song(title, artist);
        if (songs == null)
            songs = new JSONArray();
        songs.put(song.getJson());
    }
}
