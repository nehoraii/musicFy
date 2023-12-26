package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.logic.FtpLogic;
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

    private int chunkSize=1024*1024;

    @Autowired
    private SongsRepository songsRepository;
    @Autowired
    private ConnectionSongAlbumServer connectionSongAlbumServer;
    private int limitToSearch=5;
    private int chuckSize=1024*1024;
    public SongsVO save(SongsVO songsVO) {
        Security.decipherFromClient(songsVO);
        SongsEntity bean = new SongsEntity();
        try {
            Security.encodeToDB(songsVO);
            SongsLogic.copyProperty(songsVO, bean);
            bean = songsRepository.save(bean);
            SongsLogic.copyProperty(bean, songsVO);
            Security.decipherFromDB(songsVO);
            boolean sec=saveFtp(songsVO);
            if(sec){
                String path;
                path=FtpLogic.getPath()+"\\"+songsVO.getUserId()+"\\"+songsVO.getId();
                songsVO.setSongPath(path);
                update(songsVO);
                songsVO.setE(ErrorsEnumForSongs.GOOD);
                return songsVO;
            }
            songsVO.setE(ErrorsEnumForSongs.FTP_ERROR);
            return songsVO;
        } catch (Exception e) {
            System.out.println(e);
            songsVO.setE(ErrorsEnumForSongs.NOT_SAVED_SUCCESSFULLY);
            return songsVO;
        }
    }
    private boolean saveFtp(SongsVO songsVO) {
        Security.encodeToDB(songsVO);
        boolean sec=FtpLogic.uploadFile(songsVO.getUserId(),songsVO.getUserId(),songsVO.getTheSong(),songsVO.getId());
        Security.decipherFromDB(songsVO);
        return sec;
    }
    public ErrorsEnumForSongs delete(long id){
        Optional<SongsEntity> songE=geyById(id);
        if(!songE.isPresent()){
            return ErrorsEnumForSongs.SONG_NOT_FOUND;
        }
        SongsEntity s=getSongById(id);
        FtpLogic.deleteFile(s.getSongPath(),s.getUserId(),s.getUserId());
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
        Security.encodeToDB(songsVO);
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
        BeanUtils.copyProperties(songsEntity,songsVO);
        //Security.decipherFromDB(songsVO);
        //byte arr[]=FtpLogic.requestFileFromServer(addressFtp,portFtr,-1,-1,Long.toString(songsVO.getUserId())+"\\"+Long.toString(songsVO.getId()));
        byte arr[]=FtpLogic.requestFileFromServer(songsVO.getSongPath());
        songsVO.setTheSong(arr);
        Security.decipherFromDB(songsVO);
        Security.encodeToClient(songsVO);
        return songsVO;
    }
    public SongVoController getSongProperty(SongVoController songsVOCon) {
        SongsEntity songsEntity = getSongById(songsVOCon.getId());
        Security.decipherFromDB(songsEntity);
        BeanUtils.copyProperties(songsEntity,songsVOCon);
        byte[] arr=FtpLogic.requestFileFromServer(songsEntity.getSongPath());
        double sizeChunk=arr.length/(chuckSize*1.0);
        int size = (int) Math.ceil(sizeChunk);
        songsVOCon.setAmountOfChunks(size);
        SongsVO songsVO=new SongsVO();
        BeanUtils.copyProperties(songsVOCon,songsVO);
        Security.encodeToClient(songsVO);
        BeanUtils.copyProperties(songsVO,songsVOCon);
        return songsVOCon;
    }
    public SongVoController getChunkId(SongVoController songsVOCon) {
        SongVoController songsVO = new SongVoController();
        SongsEntity s = getSongById(songsVOCon.getId());
        byte arr[];
        byte song[];
        arr = FtpLogic.requestFileFromServer(s.getSongPath());
        int startIndex = songsVOCon.getChunkNum() * chunkSize;
        int lenOfRetArr = arr.length - (songsVOCon.getChunkNum() * chunkSize);
        if(lenOfRetArr>=chunkSize){
            song = new byte[chunkSize];
            lenOfRetArr=chunkSize;
        }
        else {
            song = new byte[lenOfRetArr];
        }
        for (int i = startIndex; i < startIndex + lenOfRetArr; i++) {
            song[i - startIndex] = arr[i];
            }
        songsVO.setTheSong(song);
        SongsVO songsVO1=new SongsVO();
        BeanUtils.copyProperties(songsVO,songsVO1);
        Security.encodeToClient(songsVO1);
        BeanUtils.copyProperties(songsVO1,songsVO);
        return songsVO;
    }
    public List<SongsVO> getSongByName(SongsVO songsVO){
        Optional<List<SongsEntity>> songsEntity;
        try {
            Security.decipherFromClient(songsVO);
            Security.encodeToDB(songsVO);
            System.out.println(songsVO.getNameSong());
            String name=songsVO.getNameSong();
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
