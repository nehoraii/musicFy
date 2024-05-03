package com.example.music_fly_project.vo;

import lombok.Data;

@Data
//אובייקט המייצג חיבור בין אלבום לשיר לקליינט על פי הנתונים הרלוונטים שהוגדרו מראש
public class ConnectionSongAlbumsVO {
    private Long id;
    private Long albumId;
    private Long songId;
}
