package com.example.music_fly_project.logic;

import com.example.music_fly_project.entity.ConnectionSongAlbumEntity;
import com.example.music_fly_project.vo.ConnectionSongAlbumsVO;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class ConnectionSongForAlbumLogic {
    public static boolean copyProperty(List<ConnectionSongAlbumEntity>listEntity, List<ConnectionSongAlbumsVO>listVO){
        ConnectionSongAlbumsVO temp=new ConnectionSongAlbumsVO();
        for (int i = 0; i <listEntity.size() ; i++) {
            try {
                BeanUtils.copyProperties(listEntity.get(i),temp);
                listVO.add(temp);
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
