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
    @Autowired
    private ConnectionSongPlayListServer connectionSongPlayListServer;
    private int limitToSearch=5;
    private int chuckSize=1024*1024;
    public SongsVO save(SongVoController songVoController) {
        SongsVO songsVO=new SongsVO();
        BeanUtils.copyProperties(songVoController,songsVO);
        Security.decipherFromClient(songsVO);
        if(songsVO.getId()==-1){
            long songId=saveToDB(songsVO);
            boolean sec;
            sec=FtpLogic.createDirectory(songId,songsVO.getUserId(),songsVO.getUserId());
            if(sec){
                String path=FtpLogic.getPath()+"\\"+songVoController.getUserId()+"\\"+songId;
                songsVO.setSongPath(path);
            }
            songsVO.setId(songId);
            ErrorsEnumForSongs e=update(songsVO);
            songsVO.setE(e);
        }
        boolean ans=saveFtp(songsVO,songVoController.getChunkNum());
        if(ans){
            songsVO.setE(ErrorsEnumForSongs.GOOD);
        }
        return songsVO;
    }
    private long saveToDB(SongsVO songsVO){
        Security.encodeToDB(songsVO);
        SongsEntity songsEntity=new SongsEntity();
        BeanUtils.copyProperties(songsVO,songsEntity);
        songsEntity=songsRepository.save(songsEntity);
        Security.decipherFromDB(songsVO);
        return songsEntity.getId();
    }
    private boolean saveFtp(SongsVO songsVO,int chuckId) {
        Security.encodeToDB(songsVO);
        boolean sec=FtpLogic.uploadFile(songsVO.getUserId(),songsVO.getUserId(),songsVO.getId(),songsVO.getTheSong(),chuckId);
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
        connectionSongPlayListServer.delCon(id);
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
        Security.decipherFromDB(songsVO);
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
   /* public SongsVO getSongById(SongsVO songsVO){
        SongsEntity songsEntity=getSongById(songsVO.getId());
        BeanUtils.copyProperties(songsEntity,songsVO);
        //Security.decipherFromDB(songsVO);
        //byte arr[]=FtpLogic.requestFileFromServer(addressFtp,portFtr,-1,-1,Long.toString(songsVO.getUserId())+"\\"+Long.toString(songsVO.getId()));
        byte arr[]=FtpLogic.requestFileFromServer(songsVO.getSongPath());
        songsVO.setTheSong(arr);
       // Security.decipherFromDB(songsVO);
        //Security.encodeToClient(songsVO);
        return songsVO;
    }
    */
    public SongVoController getSongProperty(SongVoController songsVOCon) {
        SongsEntity songsEntity = getSongById(songsVOCon.getId());
        Security.decipherFromDB(songsEntity);
        BeanUtils.copyProperties(songsEntity,songsVOCon);
        int amountOfFiles=FtpLogic.getAmountOfFiles(songsEntity.getSongPath());
        songsVOCon.setAmountOfChunks(amountOfFiles);
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
        arr = FtpLogic.requestFileFromServer(s.getSongPath()+"\\"+songsVOCon.getChunkNum());
        SongsVO songsVO1=new SongsVO();
        songsVO1.setTheSong(arr);
        Security.decipherFromDB(songsVO1);
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
            //Security.decipherFromDB(listEntity.get().get(i));
        }
        List<SongsVO> listVo;
        listVo=SongsLogic.copyListEntityToVO(listEntity.get());
        for (int i = 0; i <listVo.size(); i++) {
            //Security.encodeToClient(listVo.get(i));
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
