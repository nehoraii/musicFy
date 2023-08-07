package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.logic.Copyright;
import com.example.music_fly_project.logic.SongsLogic;
import com.example.music_fly_project.repository.SongsRepository;
import com.example.music_fly_project.vo.ConnectionSongPlayListVO;
import com.example.music_fly_project.vo.SongsVO;
import com.example.music_fly_project.vo.TwoIdSongs;
import com.example.music_fly_project.vo.UserVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongsServer {
    @Autowired
    private SongsRepository songsRepository;
    public SongsVO save(SongsVO songsVO){
        SongsEntity bean= new SongsEntity();
        SongsLogic.copyProperty(songsVO,bean);
        try {
            bean=songsRepository.save(bean);
            SongsLogic.copyProperty(bean,songsVO);
        }catch (Exception e){
            System.out.println(e);
            songsVO.setE(ErrorsEnumForSongs.NOT_SAVED_SUCCESSFULLY);
            return songsVO;
        }
        songsVO.setE(ErrorsEnumForSongs.GOOD);
        return songsVO;
    }
    public ErrorsEnumForSongs delete(long id){
        Optional<SongsEntity> songE=geyById(id);
        if(!songE.isPresent()){
            return ErrorsEnumForSongs.SONG_NOT_FOUND;
        }
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
    public ErrorsEnumForSongs checkCopyright(SongsVO song1 , SongsVO song2){
        ErrorsEnumForSongs e;
        e=SongsLogic.checkSong(song1.getTheSong(),song2.getTheSong());
        return e;
    }
    private SongsEntity getSongById(long songId){
        SongsEntity song;
        song =songsRepository.getReferenceById(songId);
        return song;
    }
    public SongsVO getSongById(SongsVO songsVO){
        SongsEntity songsEntity=getSongById(songsVO.getId());
        BeanUtils.copyProperties(songsEntity,songsVO);
        return songsVO;
    }
    public List<SongsVO> getSongByName(SongsVO songsVO){
        Optional<List<SongsEntity>> songsEntity;
        try {
            String name=songsVO.getNameSong();
            songsEntity=songsRepository.getSongByName(name);

        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        if(!songsEntity.isPresent()||songsEntity.get().size()==0){
            return null;
        }
        List<SongsVO>listVo;
       listVo=SongsLogic.copyListEntityToVO(songsEntity.get());
        return listVo;
    }
    public List<SongsVO> getSongsByUserId(UserVO userVO){
        Optional<List<SongsEntity>>listEntity=songsRepository.getSongsByUserId(userVO.getId());
        if(!listEntity.isPresent()){
            return null;
        }
        List<SongsVO> listVo;
        listVo=SongsLogic.copyListEntityToVO(listEntity.get());
        return listVo;
    }
    public ErrorsEnumForSongs getCopyright(TwoIdSongs twoIdSongs){
        SongsEntity firstSong,secSong;
        firstSong=getSongById(twoIdSongs.getSourceId());
        secSong=getSongById(twoIdSongs.getImitationId());
        if(firstSong.getId()==secSong.getId()){
            return ErrorsEnumForSongs.THE_SAME_SONG;
        }
            if(firstSong.getUserId()== secSong.getUserId()){
            return ErrorsEnumForSongs.YOURS_SONGS;
        }
        boolean answer=Copyright.compareFirst5Seconds(firstSong.getTheSong(),secSong.getTheSong());
        if(answer){
            return ErrorsEnumForSongs.COPYRIGHT;
        }
        return ErrorsEnumForSongs.LAYER;
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
