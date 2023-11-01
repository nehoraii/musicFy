package com.example.music_fly_project.logic;

import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.vo.PlayListVO;

import java.util.ArrayList;
import java.util.List;

public class PlayListLogic {
    public static List<PlayListVO> copyList(List<PlayListEntity> list){
        List<PlayListVO> listVOS=new ArrayList<>();
        for (int i = 0; i < list.size() ; i++) {
            PlayListVO playListVO=new PlayListVO();
            playListVO.setDate(list.get(i).getDate());
            playListVO.setPlayListName(list.get(i).getPlayListName());
            playListVO.setLengthPlayList(list.get(i).getLengthPlayList());
            playListVO.setId(list.get(i).getId());
            playListVO.setImage(list.get(i).getImage());
            playListVO.setPublic1(list.get(i).isPublic1());
            playListVO.setUserId(list.get(i).getUserId());
            listVOS.add(playListVO);
        }
        return listVOS;
    }
}
