package com.example.music_fly_project.vo;

import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import lombok.Data;

import java.util.Date;
@Data
public class SongsVO {
    private long id;
    private long userId;
    private byte[] theSong;
    private String zaner;
    private Date date;
    private String nameSong;
    private String songPath;
    private ErrorsEnumForSongs e;
}
