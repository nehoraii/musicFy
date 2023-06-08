package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.logic.SongsLogic;
import com.example.music_fly_project.repository.SongsRepository;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SongsServer {
    @Autowired
    private SongsRepository songsRepository;
    public ErrorsEnumForSongs save(SongsVO songsVO){
        SongsEntity bean= new SongsEntity();
        BeanUtils.copyProperties(songsVO,bean);
        songsRepository.save(bean);
        return ErrorsEnumForSongs.GOOD;
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

}
