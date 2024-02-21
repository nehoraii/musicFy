package com.example.music_fly_project.vo;

import lombok.Data;

import java.util.Date;
@Data
public class SongVoController {
    private long id;
    private long userId;
    private byte[] theSong;
    private Date date;
    private String nameSong;
    private String zaner;
    private int amountOfChunks;
    private int chunkNum;
}
