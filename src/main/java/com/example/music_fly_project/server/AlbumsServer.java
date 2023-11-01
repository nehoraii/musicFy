package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import com.example.music_fly_project.logic.AlbumsLogic;
import com.example.music_fly_project.logic.Security;
import com.example.music_fly_project.repository.AlbumsRepository;
import com.example.music_fly_project.vo.AlbumsVO;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumsServer {
    @Autowired
    private AlbumsRepository albumsRepository;
    private int limitAlbumsSearch=5;
    public AlbumsVO save(AlbumsVO albumsVO){
        AlbumsEntity bean =new AlbumsEntity();
        AlbumsLogic.copyProperty(albumsVO,bean);
        try {
            bean=albumsRepository.save(bean);
            AlbumsLogic.copyProperty(bean,albumsVO);
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
    public List<SongsVO> getSongsInAlbum(Long albumId){
        Optional<List<SongsEntity>> list;
        list=albumsRepository.getAlbumsSongByAlbumId(albumId);
        if(!list.isPresent()){
            return null;
        }
        List<SongsVO> listId=new ArrayList<>();
        for (int i = 0; i < list.get().size(); i++) {
            SongsEntity songsEntity=new SongsEntity();
            BeanUtils.copyProperties(list.get().get(i),songsEntity);
            Security.decipherFromDB(songsEntity);
            SongsVO songsVO=new SongsVO();
            BeanUtils.copyProperties(songsEntity,songsVO);
            Security.encodeToClient(songsVO);
            listId.add(songsVO);
        }
        return listId;
    }
    public List<AlbumsVO> getAlbumOfUser(Long userId){
        Optional<List<AlbumsEntity>> list;
        list=albumsRepository.getAlbumOfUser(userId);
        if(!list.isPresent()){
            return null;
        }
        List listVo=new ArrayList<>();
        for (int i = 0; i < list.get().size(); i++) {
            AlbumsVO albumsVO=new AlbumsVO();
            AlbumsLogic.copyProperty(list.get().get(i),albumsVO);
            listVo.add(albumsVO);
        }
        return listVo;
    }
    public AlbumsVO getAlbumById(Long albumId){
        Optional<AlbumsEntity> albumEntity;
        albumEntity=getById(albumId);
        if(!albumEntity.isPresent()){
            return null;
        }
        AlbumsVO albumsVO=new AlbumsVO();
        AlbumsLogic.copyProperty(albumEntity.get(),albumsVO);
        return albumsVO;
    }
    public List<Long> getSongsIdByAlbumId(Long albumId){
        Optional<List<Object>> list;
        list=albumsRepository.getAlbumsSongIdByAlbumId(albumId);
        if(!list.isPresent()){
            return null;
        }
        List<Long> listId=new ArrayList<>();
        Long someId;
        for (int i = 0; i < list.get().size(); i++) {
            someId=(Long) list.get().get(i);
            listId.add(someId);
        }
        return listId;
    }
    public List<AlbumsVO> getAlbumsByName(AlbumsVO albumsVO){
        Optional<List<AlbumsEntity>>listEntity;
        listEntity=albumsRepository.getAlbumsByName(albumsVO.getNameAlbum(), albumsVO.getId(), limitAlbumsSearch);
        if(!listEntity.isPresent()){
            return null;
        }
        List<AlbumsVO>listVo=new ArrayList<>();
        for (int i = 0; i < listEntity.get().size(); i++) {
            AlbumsVO someAlbum=new AlbumsVO();
            BeanUtils.copyProperties(listEntity.get().get(i),someAlbum);
            listVo.add(someAlbum);
        }
        return listVo;
    }
}
