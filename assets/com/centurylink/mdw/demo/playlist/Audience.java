package com.centurylink.mdw.demo.playlist;

import org.json.JSONObject;

import com.centurylink.mdw.model.Jsonable;

public class Audience implements Jsonable {
    
    public enum Genre {
        folk,
        punk
    }
    
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private int yearOfBirth;
    public int getYearOfBirth() { return yearOfBirth; }
    public void setYearOfBirth(int year) { this.yearOfBirth = year; }
    
    private Genre genre;
    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }
    
    public Audience(JSONObject json) {
        bind(json);
    }
}
