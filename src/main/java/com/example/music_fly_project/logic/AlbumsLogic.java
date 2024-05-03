package com.example.music_fly_project.logic;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.vo.AlbumsVO;
//כאן יש לנו את כל הלוגיקה החיצונית שנצטרך בשביל האובייקט המתאים
public class AlbumsLogic {

    /*
    מעתיקה נתונים הרלוונטים בין שתי אובייקטים
    */
    public static void copyProperty(AlbumsEntity from, AlbumsVO to){
        to.setImageAlbum(from.getImageAlbum());
        to.setNameAlbum(from.getNameAlbum());
        to.setUserId(from.getUserId());
        to.setId(from.getId());
    }
}
