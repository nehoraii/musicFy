package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.repository.SongsRepository;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SongsServer {
    @Autowired
    private SongsRepository songsRepository;
    public long save(SongsVO songsVO){
        SongsEntity bean= new SongsEntity();
        BeanUtils.copyProperties(songsVO,bean);
        bean=songsRepository.save(bean);
        return bean.getId();
    }
    public long delete(long id){
        songsRepository.deleteById(id);
        return id;
    }
    public long update(SongsVO songsVO){
        SongsEntity bean;
        try{
            bean=geyById(songsVO.getId());
            return bean.getId();
        }catch (Exception e){
            e.getMessage();
            return -1;
        }
    }
    private SongsEntity geyById(long id){
        SongsEntity user=songsRepository.findById(id).orElseThrow(()->new NoSuchElementException("Not Found!!!"));
        return user;
    }
}
