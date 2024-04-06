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
    @Autowired
    private SongsRepository songsRepository;
    @Autowired
    private ConnectionSongAlbumServer connectionSongAlbumServer;
    @Autowired
    private ConnectionSongPlayListServer connectionSongPlayListServer;
    private int limitToSearch=5;
    public SongsVO save(SongsVO songsVO) {
        Security.decipherFromClient(songsVO);
        if(songsVO.getId()==-1){
            long songId=saveToDB(songsVO);
            songsVO.setId(songId);
            boolean sec;
            ErrorsEnumForSongs e=ErrorsEnumForSongs.GOOD;
            sec=FtpLogic.createDirectory(songId,songsVO.getUserId(),songsVO.getUserId());
            if(sec){
                String path=FtpLogic.getPath()+"\\"+songsVO.getUserId()+"\\"+songId;
                e=update(songsVO,path);
            }
            songsVO.setE(e);
        }
        boolean ans=saveFtp(songsVO,songsVO.getChunkNum());
        if(ans){
            songsVO.setE(ErrorsEnumForSongs.GOOD);
        }
        songsVO.setNameSong(null);
        songsVO.setDate(null);
        songsVO.setZaner(null);
        songsVO.setUserId(null);
        //הקליינט לא צריך את הנתונים הנ"ל בעת הוספת שיר למערכת
        return songsVO;
    }
    private Long saveToDB(SongsVO songsVO){
        Security.encodeToDB(songsVO);
        SongsEntity songsEntity=new SongsEntity();
        BeanUtils.copyProperties(songsVO,songsEntity);
        songsEntity=songsRepository.save(songsEntity);
        Security.decipherFromDB(songsVO);
        return songsEntity.getId();
    }
    private boolean saveFtp(SongsVO songsVO,int chuckId) {
        Security.encodeToDB(songsVO);
        boolean sec=FtpLogic.uploadFile(songsVO.getUserId(),songsVO.getUserId(),songsVO.getId(),songsVO.getTheSong(), Long.valueOf(chuckId));
        Security.decipherFromDB(songsVO);
        return sec;
    }

    public ErrorsEnumForSongs delete(Long id){
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
    private ErrorsEnumForSongs update(SongsVO songsVO,String path) {
        Optional<SongsEntity> songE=geyById(songsVO.getId());
        if(!songE.isPresent()){
            return ErrorsEnumForSongs.SONG_NOT_FOUND;
        }
        SongsEntity bean= new SongsEntity();
        bean.setSongPath(path);
        Security.encodeToDB(songsVO);
        BeanUtils.copyProperties(songsVO,bean);
        songsRepository.save(bean);
        Security.decipherFromDB(songsVO);
        return ErrorsEnumForSongs.GOOD;
    }
    private Optional<SongsEntity> geyById(Long id){
        Optional<SongsEntity> user=songsRepository.findById(id);
        return user;
    }
    private SongsEntity getSongById(Long songId){
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
    public SongsVO getSongProperty(SongsVO songsVO) {
        SongsEntity songsEntity = getSongById(songsVO.getId());
        BeanUtils.copyProperties(songsEntity,songsVO);
        Security.decipherFromDB(songsVO);
        int amountOfFiles=FtpLogic.getAmountOfFiles(songsEntity.getSongPath());
        songsVO.setAmountOfChunks(amountOfFiles);
        Security.encodeToClient(songsVO);
        return songsVO;
    }
    public SongsVO getChunkId(SongsVO songsVO) {
        SongsEntity s = getSongById(songsVO.getId());
        byte arr[];
        arr = FtpLogic.requestFileFromServer(s.getSongPath()+"\\"+songsVO.getChunkNum());
        songsVO.setTheSong(arr);
        Security.decipherFromDB(songsVO);
        Security.encodeToClient(songsVO);
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
        List<SongsVO>listVo;
        listVo=SongsLogic.copyListEntityToVO(songsEntity.get());
        for (int i = 0; i < listVo.size(); i++) {
            Security.decipherFromDB(listVo.get(i));
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
