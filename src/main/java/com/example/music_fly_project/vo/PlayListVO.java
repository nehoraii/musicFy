package com.example.music_fly_project.vo;

import com.example.music_fly_project.enums.ErrorsEnumForPlayList;
import lombok.Data;

import java.util.Date;
@Data
//אובייקט המייצג פלייליסט לקליינט על פי הנתונים הרלוונטים שהוגדרו מראש
public class PlayListVO {
    private Long id;
    private Long userId;
    private String playListName;
    private int lengthPlayList;
    private boolean public1;
    private Date date;
    private byte[] image;
    private ErrorsEnumForPlayList e;


}
