package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.ConnectionSongPlayListEntity;
import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForPlayList;
import com.example.music_fly_project.logic.ConnectionSongForPlayList;
import com.example.music_fly_project.logic.PlayListLogic;
import com.example.music_fly_project.logic.Security;
import com.example.music_fly_project.logic.SongsLogic;
import com.example.music_fly_project.repository.PlayListRepository;
import com.example.music_fly_project.vo.ConnectionSongPlayListVO;
import com.example.music_fly_project.vo.PlayListVO;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayListServer {
    @Autowired
    private PlayListRepository playListRepository;
    public ErrorsEnumForPlayList save(PlayListVO playListVO){
        PlayListEntity bean= new PlayListEntity();
        playListVO.setDate(new Date());
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
    public List<PlayListVO> getPlayListByName(PlayListVO playListVO) {
        Optional<List<PlayListVO>>list;
        list=playListRepository.getPlayListByName(playListVO.getPlayListName(),playListVO.getUserId());
        if(!list.isPresent()){
            return null;
        }
        return list.get();

    }
    public List<PlayListVO> getPlayListByUserId(PlayListVO playListVO){
        Optional<List<PlayListEntity>>list;
        list=playListRepository.getPlayListUserId(playListVO.getUserId());
        if(!list.isPresent()){
            return null;
        }
        PlayListLogic playListLogic=new PlayListLogic();
        List<PlayListVO> listVOS;
        listVOS=playListLogic.copyList(list.get());
        return listVOS;
    }
    public List<SongsVO> getPlayListById(PlayListVO playListVO){
        Optional<List<SongsEntity>> list;
        list=playListRepository.getPlayListByPlayListId(playListVO.getId());
        if(!list.isPresent()){
            return null;
        }
        for (int i = 0; i <list.get().size() ; i++) {
            Security.decipherFromDB(list.get().get(i));
        }
        SongsLogic songsLogic=new SongsLogic();
        List<SongsVO>voList;
        voList=songsLogic.copyListEntityToVO(list.get());
        for (int i = 0; i < voList.size(); i++) {
            Security.encodeToClient(voList.get(i));
        }
        return voList;
    }
}
