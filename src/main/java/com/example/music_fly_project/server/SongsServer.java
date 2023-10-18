package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.logic.Security;
import com.example.music_fly_project.logic.SongsLogic;
import com.example.music_fly_project.repository.SongsRepository;
import com.example.music_fly_project.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SongsServer {
    @Autowired
    private SongsRepository songsRepository;
    @Autowired
    private ConnectionSongAlbumServer connectionSongAlbumServer;
    private int limitToSearch=5;
    public SongsVO save(SongsVO songsVO) {
        Security.decipherFromClient(songsVO);
        SongsEntity bean = new SongsEntity();
        SongsLogic.copyProperty(songsVO, bean);
        try {
            Security.encodeToDB(bean);
            bean = songsRepository.save(bean);
            Security.decipherFromDB(bean);
            SongsLogic.copyProperty(bean, songsVO);
        } catch (Exception e) {
            System.out.println(e);
            songsVO.setE(ErrorsEnumForSongs.NOT_SAVED_SUCCESSFULLY);
            return songsVO;
        }
        songsVO.setE(ErrorsEnumForSongs.GOOD);
        Security.encodeToClient(songsVO);
        return songsVO;
    }
    public ErrorsEnumForSongs delete(long id){
        Optional<SongsEntity> songE=geyById(id);
        if(!songE.isPresent()){
            return ErrorsEnumForSongs.SONG_NOT_FOUND;
        }
        connectionSongAlbumServer.delConBySongId(id);
        songsRepository.deleteById(id);
        return ErrorsEnumForSongs.GOOD;
    }
    public ErrorsEnumForSongs update(SongsVO songsVO) {
        Optional<SongsEntity> songE=geyById(songsVO.getId());
        if(!songE.isPresent()){
            return ErrorsEnumForSongs.SONG_NOT_FOUND;
        }
        SongsEntity bean= new SongsEntity();
        BeanUtils.copyProperties(songsVO,bean);
        songsRepository.save(bean);
        return ErrorsEnumForSongs.GOOD;
    }
    private Optional<SongsEntity> geyById(long id){
        Optional<SongsEntity> user=songsRepository.findById(id);
        return user;
    }
    private SongsEntity getSongById(long songId){
        SongsEntity song;
        song =songsRepository.getReferenceById(songId);
        return song;
    }
    public SongsVO getSongById(SongsVO songsVO){
        SongsEntity songsEntity=getSongById(songsVO.getId());
        Security.decipherFromDB(songsEntity);
        BeanUtils.copyProperties(songsEntity,songsVO);
        Security.encodeToClient(songsVO);
        return songsVO;
    }
    public List<SongsVO> getSongByName(SongsVO songsVO){
        Optional<List<SongsEntity>> songsEntity;
        try {
            Security.decipherFromClient(songsVO);
            SongsEntity songsEntity1=new SongsEntity();
            SongsLogic.copyProperty(songsVO,songsEntity1);
            Security.encodeToDB(songsEntity1);
            System.out.println(songsEntity1.getNameSong());
            String name=songsEntity1.getNameSong();
            songsEntity=songsRepository.getSongByName(name, songsVO.getId(),limitToSearch);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        if(!songsEntity.isPresent()||songsEntity.get().size()==0){
            return null;
        }
        for (int i = 0; i < songsEntity.get().size(); i++) {
            Security.decipherFromDB(songsEntity.get().get(i));
        }
        List<SongsVO>listVo;
        listVo=SongsLogic.copyListEntityToVO(songsEntity.get());
        for (int i = 0; i < listVo.size(); i++) {
            Security.encodeToClient(listVo.get(i));
        }
        return listVo;
    }
    public List<SongsVO> getSongsByUserId(UserVO userVO){
        Optional<List<SongsEntity>>listEntity=songsRepository.getSongsByUserId(userVO.getId());
        if(!listEntity.isPresent()){
            return null;
        }
        for (int i = 0; i < listEntity.get().size(); i++) {
            Security.decipherFromDB(listEntity.get().get(i));
        }
        List<SongsVO> listVo;
        listVo=SongsLogic.copyListEntityToVO(listEntity.get());
        for (int i = 0; i <listVo.size(); i++) {
            Security.encodeToClient(listVo.get(i));
        }
        return listVo;
    }
    public byte[] getImageSong(Long songId){
        Optional<AlbumsEntity> albums;
        albums=songsRepository.getAlbumBySongId(songId);
        if(!albums.isPresent()){
            return null;
        }
        byte[] image=albums.get().getImageAlbum();
        return image;
    }
}
