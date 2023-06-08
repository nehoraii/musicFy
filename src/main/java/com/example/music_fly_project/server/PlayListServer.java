package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.enums.ErrorsEnumForPlayList;
import com.example.music_fly_project.repository.PlayListRepository;
import com.example.music_fly_project.vo.PlayListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PlayListServer {
    @Autowired
    private PlayListRepository playListRepository;
    public ErrorsEnumForPlayList save(PlayListVO playListVO){
        PlayListEntity bean= new PlayListEntity();
        BeanUtils.copyProperties(playListVO,bean);
        playListRepository.save(bean);
        return ErrorsEnumForPlayList.GOOD;
    }
    public ErrorsEnumForPlayList delete(long id){
        Optional<PlayListEntity>playList;
        playList=geyById(id);
        if(!playList.isPresent()){
            return ErrorsEnumForPlayList.PLAY_LIST_NOT_FOUND;
        }
        playListRepository.deleteById(id);
        return ErrorsEnumForPlayList.GOOD;
    }
    public ErrorsEnumForPlayList update(PlayListVO playListVO){
        Optional<PlayListEntity> bean;
        try{
            bean=geyById(playListVO.getId());
            if(!bean.isPresent()){
                return ErrorsEnumForPlayList.PLAY_LIST_NOT_FOUND;
            }
            BeanUtils.copyProperties(playListVO,bean.get());
            playListRepository.save(bean.get());
            return ErrorsEnumForPlayList.GOOD;
        }catch (Exception e){
            e.getMessage();
            return ErrorsEnumForPlayList.ELSE_ERROR;
        }
    }
    private  Optional<PlayListEntity> geyById(long id){
        Optional<PlayListEntity> user=playListRepository.findById(id);
        return user;
    }

}
