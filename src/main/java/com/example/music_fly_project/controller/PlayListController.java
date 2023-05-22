package com.example.music_fly_project.controller;

import com.example.music_fly_project.server.PlayListServer;
import com.example.music_fly_project.vo.PlayListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/PlayList")
public class PlayListController {
    @Autowired
    private PlayListServer playListServer;
    @PostMapping("/save")
    public long save(@RequestBody PlayListVO playListVO){
        return playListServer.save(playListVO);
    }
    @DeleteMapping("/delete")
    public long delete(@RequestBody PlayListVO playListVO){
        return playListServer.delete(playListVO.getId());
    }
    @PutMapping("/update")
    public long update(@RequestBody PlayListVO playListVO){
        long userId=playListServer.update(playListVO);
        return userId;
    }
}
