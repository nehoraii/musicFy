package com.example.music_fly_project.logic;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import com.example.music_fly_project.vo.AlbumsVO;

public class AlbumsLogic {
    public static void copyProperty(AlbumsVO from, AlbumsEntity to){
        to.setImageAlbum(from.getImageAlbum());
        to.setNameAlbum(from.getNameAlbum());
        to.setUserId(from.getUserId());
        to.setId(from.getId());
    }
    public static void copyProperty(AlbumsEntity from, AlbumsVO to){
        to.setImageAlbum(from.getImageAlbum());
        to.setNameAlbum(from.getNameAlbum());
        to.setUserId(from.getUserId());
        to.setId(from.getId());
    }
}
