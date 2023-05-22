package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.repository.PlayListRepository;
import com.example.music_fly_project.vo.PlayListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PlayListServer {
    @Autowired
    private PlayListRepository playListRepository;
    public long save(PlayListVO playListVO){
        PlayListEntity bean= new PlayListEntity();
        BeanUtils.copyProperties(playListVO,bean);
        bean=playListRepository.save(bean);
        return bean.getId();
    }
    public long delete(long id){
        playListRepository.deleteById(id);
        return id;
    }
    public long update(PlayListVO playListVO){
        PlayListEntity bean;
        try{
            bean=geyById(playListVO.getId());
            return bean.getId();
        }catch (Exception e){
            e.getMessage();
            return -1;
        }
    }
    private  PlayListEntity geyById(long id){
        PlayListEntity user=playListRepository.findById(id).orElseThrow(()->new NoSuchElementException("Not Found!!!"));
        return user;
    }

}
