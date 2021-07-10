package com.unicocoder.batmanmovies.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "batman")
public class Batman {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "imdbID")
    private String imdbID;

    @ColumnInfo(name = "poster")
    private String poster;

    @ColumnInfo(name = "title")
    private String title;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
