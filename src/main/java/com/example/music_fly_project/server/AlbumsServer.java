package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import com.example.music_fly_project.logic.AlbumsLogic;
import com.example.music_fly_project.repository.AlbumsRepository;
import com.example.music_fly_project.vo.AlbumsVO;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlbumsServer {
    @Autowired
    private AlbumsRepository albumsRepository;
    public AlbumsVO save(AlbumsVO albumsVO){
        AlbumsEntity bean =new AlbumsEntity();
        AlbumsLogic.copyProperty(albumsVO,bean);
        try {
            bean=albumsRepository.save(bean);
        }catch (Exception e){
            System.out.println(e);
            albumsVO.setE(ErrorsEnumForAlbums.NotSavedSuccessfully);
            return albumsVO;
        }
        albumsVO.setE(ErrorsEnumForAlbums.GOOD);
        return albumsVO;
    }
    public ErrorsEnumForAlbums delete(long id){
        Optional<AlbumsEntity> albums;
        albums=getById(id);
        if(!albums.isPresent()){
            return ErrorsEnumForAlbums.AlbumsNotFound;
        }
        albumsRepository.deleteById(id);
        return ErrorsEnumForAlbums.GOOD;
    }
    public ErrorsEnumForAlbums update(AlbumsVO albumsVO){
        Optional<AlbumsEntity> albums;
        albums=getById(albumsVO.getId());
        if(!albums.isPresent()){
            return ErrorsEnumForAlbums.AlbumsNotFound;
        }
//        if(albumsVO.equals(albums.get())){
//            return ErrorsEnumForAlbums.TheSameAlbum;
//        }
        AlbumsEntity bean=new AlbumsEntity();
        BeanUtils.copyProperties(albumsVO,bean);
        albumsRepository.save(bean);
        return ErrorsEnumForAlbums.GOOD;
    }
    private Optional<AlbumsEntity> getById(long id){
        return albumsRepository.findById(id);
    }
}
