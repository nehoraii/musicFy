package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorEnumForConSongPlayList;
import com.example.music_fly_project.server.ConnectionSongPlayListServer;
import com.example.music_fly_project.server.PlayListServer;
import com.example.music_fly_project.vo.ConnectionSongPlayListVO;
import com.example.music_fly_project.vo.PlayListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/ConSongPlayList")
@RestController
@CrossOrigin("*")
public class ConnectionSongPlayListController {
    @Autowired
    private ConnectionSongPlayListServer server;
    @Autowired
    private PlayListServer playListServer;
    @PostMapping("/save")
    public ErrorEnumForConSongPlayList save(@RequestBody ConnectionSongPlayListVO connectionSongPlayListVO){
        ErrorEnumForConSongPlayList e;
        e=server.save(connectionSongPlayListVO);
        playListServer.updateLengthPlayList(connectionSongPlayListVO.getPlayListId(), connectionSongPlayListVO.getSongId());
        return e;
    }
    @DeleteMapping("/delete")
    public ErrorEnumForConSongPlayList delete(@RequestBody ConnectionSongPlayListVO connectionSongPlayListVO){
        ErrorEnumForConSongPlayList e;
        e=server.delete(connectionSongPlayListVO);
        return e;
    }
    /*
    @PutMapping("/update")
    public ErrorEnumForConSongPlayList update(@RequestBody twoConnectionSongPlayListVO con){
        ErrorEnumForConSongPlayList e;
        e=server.update(con);
        return e;
    }
    */
}
