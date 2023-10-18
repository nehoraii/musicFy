package com.example.music_fly_project.vo;

import com.example.music_fly_project.enums.ErrorsEnumForPlayList;
import lombok.Data;

import java.util.Date;
@Data
public class PlayListVO {
    private Long id;
    private long userId;
    private String playListName;
    private long lengthPlayList;
    private boolean public1;
    private Date date;
    private byte[] image;
    private ErrorsEnumForPlayList e;


}
