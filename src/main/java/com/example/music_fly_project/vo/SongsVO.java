package com.example.music_fly_project.vo;

import java.util.Date;

public class SongsVO {
    private Long id;
    private String nameSong;
    private long userId;
    private byte[] theSong;
    private String zaner;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public byte[] getTheSong() {
        return theSong;
    }

    public void setTheSong(byte[] theSong) {
        this.theSong = theSong;
    }

    public String getZaner() {
        return zaner;
    }

    public void setZaner(String zaner) {
        this.zaner = zaner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
