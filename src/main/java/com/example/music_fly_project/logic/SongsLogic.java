package com.example.music_fly_project.logic;

import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.vo.SongsVO;
import java.util.ArrayList;
import java.util.List;

public class SongsLogic {
    public static List<SongsVO> copyListEntityToVO(List<SongsEntity> listEntity){
        List<SongsVO> list=new ArrayList<>();
        SongsVO songsVO;
        for (int i = 0; i < listEntity.size(); i++) {
            songsVO=new SongsVO();
            songsVO.setNameSong(listEntity.get(i).getNameSong());
            songsVO.setZaner(listEntity.get(i).getZaner());
            songsVO.setId(listEntity.get(i).getId());
            songsVO.setTheSong(null);
            songsVO.setDate(listEntity.get(i).getDate());
            songsVO.setUserId(listEntity.get(i).getUserId());
            list.add(songsVO);
        }
        return list;
    }

}
