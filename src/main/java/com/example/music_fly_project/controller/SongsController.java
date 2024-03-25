package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.server.SongsServer;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/Song")
@CrossOrigin(origins = "*")
public class SongsController {
    @Autowired
    private SongsServer songsServer;
    @PostMapping("/save")
    public SongsVO save(@RequestBody SongsVO songsVO){
        SongsVO e;
        e= songsServer.save(songsVO);
        return e;
    }
    @DeleteMapping("/delete")
    public ErrorsEnumForSongs delete(@RequestBody SongsVO songsVO){
        ErrorsEnumForSongs e;
        e=songsServer.delete(songsVO.getId());
        return e;
    }
    /*@PutMapping("/update")
    public ErrorsEnumForSongs update(@RequestBody SongsVO songsVO){
        ErrorsEnumForSongs e;
        e=songsServer.update(songsVO);
        return e;
    }
    */
    @PostMapping("/getProperties")
    public SongsVO getSongProperty(@RequestBody SongsVO songsVO){
        songsVO=songsServer.getSongProperty(songsVO);
        return songsVO;
    }
    @PostMapping("/getChunk")
    public SongsVO getTheSongById(@RequestBody SongsVO songsVO){
        SongsVO song=songsServer.getChunkId(songsVO);
        return song;
    }

    @PostMapping("/getSongByName")
    public List<SongsVO> getSongsByName(@RequestBody SongsVO songsVO){
        List<SongsVO> list=songsServer.getSongByName(songsVO);
        return list;
    }
    @PostMapping("/getImageSong")
    public byte[] getImageSong(@RequestBody SongsVO songsVO){
        byte[] image;
        image=songsServer.getImageSong(songsVO.getId());
        return image;
    }

}
