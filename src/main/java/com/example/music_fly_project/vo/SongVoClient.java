package com.example.music_fly_project.vo;

import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import lombok.Data;

import java.util.Date;

@Data
public class SongVoClient {
    private long id;
    private long userId;
    private String theSong;
    private String zaner;
    private Date date;
    private String nameSong;
    private ErrorsEnumForSongs e;
}
