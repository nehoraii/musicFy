package com.example.music_fly_project.logic;

import com.example.music_fly_project.entity.ConnectionSongPlayListEntity;
import com.example.music_fly_project.vo.ConnectionSongPlayListVO;

import java.util.ArrayList;
import java.util.List;

public class ConnectionSongForPlayList {
    public static List<ConnectionSongPlayListVO>copyList(List<ConnectionSongPlayListEntity> list){
        List<ConnectionSongPlayListVO> listVOS=new ArrayList<>();
        ConnectionSongPlayListVO con=new ConnectionSongPlayListVO();
        for (int i = 0; i < list.size(); i++) {
            con.setId(list.get(i).getId());
            con.setPlayListId(list.get(i).getPlayListId());
            con.setSongId(list.get(i).getSongId());
            listVOS.add(con);
        }
        return listVOS;
    }
}
