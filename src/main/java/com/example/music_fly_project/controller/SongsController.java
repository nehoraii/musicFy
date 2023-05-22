package com.example.music_fly_project.controller;

import com.example.music_fly_project.server.SongsServer;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/Song")
public class SongsController {
    @Autowired
    private SongsServer songsServer;
    @PostMapping("/save")
    public long save(@RequestBody SongsVO songsVO){
        return songsServer.save(songsVO);
    }
    @DeleteMapping("/delete")
    public long delete(@RequestBody SongsVO songsVO){
        return songsServer.delete(songsVO.getId());
    }
    @PutMapping("/update")
    public long update(@RequestBody SongsVO songsVO){
        long userId=songsServer.update(songsVO);
        return userId;
    }

}
