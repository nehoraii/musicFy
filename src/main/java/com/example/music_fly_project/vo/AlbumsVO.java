package com.example.music_fly_project.vo;

import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import lombok.Data;

@Data
public class AlbumsVO {
    private Long id;
    private long userId;
    private String nameAlbum;
    private byte[] imageAlbum;
    private long lengthAlbum;
    private ErrorsEnumForAlbums e;

}
