package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.ConnectionSongPlayListEntity;
import com.example.music_fly_project.enums.ErrorEnumForConSongPlayList;
import com.example.music_fly_project.repository.ConnectionSongPlayListRepository;
import com.example.music_fly_project.vo.ConnectionSongPlayListVO;
import com.example.music_fly_project.vo.PlayListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConnectionSongPlayListServer {
    @Autowired
    private ConnectionSongPlayListRepository conSongPlayListRepository;

    public ErrorEnumForConSongPlayList save(ConnectionSongPlayListVO conSongPlayList){
        ConnectionSongPlayListEntity bean=new ConnectionSongPlayListEntity();
        try {
            BeanUtils.copyProperties(conSongPlayList,bean);
            conSongPlayListRepository.save(bean);
        }catch (Exception e){
            return ErrorEnumForConSongPlayList.NOT_SAVED_SUCCESSFULLY;
        }
        return ErrorEnumForConSongPlayList.GOOD;
    }
    public ErrorEnumForConSongPlayList delete(ConnectionSongPlayListVO conSongPlayList){
        Optional<ConnectionSongPlayListEntity> con;
        con=conSongPlayListRepository.getConBySongIdAndPlayListId(conSongPlayList.getSongId(),conSongPlayList.getPlayListId());
        if(!con.isPresent()){
            return ErrorEnumForConSongPlayList.CONNECTION_NOT_FOUND;
        }
        conSongPlayListRepository.deleteById(con.get().getId());
        return ErrorEnumForConSongPlayList.GOOD;
    }



}
