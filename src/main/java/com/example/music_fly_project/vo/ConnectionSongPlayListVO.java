package com.example.music_fly_project.vo;

import lombok.Data;

@Data
//אובייקט המייצג חיבור שיר לפלייליסט לקליינט על פי הנתונים הרלוונטים שהוגדרו מראש
public class ConnectionSongPlayListVO {
    private Long id;
    private Long playListId;
    private long songId;
}
