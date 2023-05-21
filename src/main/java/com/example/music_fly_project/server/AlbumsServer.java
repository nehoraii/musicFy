package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.repository.AlbumsRepository;
import com.example.music_fly_project.vo.AlbumsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
@Service
public class AlbumsServer {
    @Autowired
    private AlbumsRepository albumsRepository;
    public long save(AlbumsVO albumsVO){
        AlbumsEntity bean =new AlbumsEntity();
        BeanUtils.copyProperties(albumsVO,bean);
        bean=albumsRepository.save(bean);
        return bean.getId();
    }
    public long delete(long id){
        albumsRepository.deleteById(id);
        return id;
    }
    public long update(AlbumsVO albumsVO){
        AlbumsEntity bean;
        try{
            bean=getById(albumsVO.getId());
        }catch (Exception e){
            e.getMessage();
            return -1;
        }
        BeanUtils.copyProperties(albumsVO,bean);
        return albumsRepository.save(bean).getId();

    }
    private AlbumsEntity getById(long id){
        return albumsRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException(id + " Not Found !!!"));
    }
}
