package com.example.music_fly_project.vo;

import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import lombok.Data;

import java.util.Date;
@Data
//אובייקט המייצג שיר לקליינט על פי הנתונים הרלוונטים שהוגדרו מראש
public class SongsVO {
    private Long id;
    private Long userId;
    private byte[] theSong;
    private String zaner;
    private Date date;
    private String nameSong;
    private ErrorsEnumForSongs e;
    private int amountOfChunks;
    private int chunkNum;
}
