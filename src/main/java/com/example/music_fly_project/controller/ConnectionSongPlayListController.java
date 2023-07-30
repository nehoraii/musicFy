package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorEnumForConSongPlayList;
import com.example.music_fly_project.server.ConnectionSongPlayListServer;
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
    @PostMapping("/save")
    public ErrorEnumForConSongPlayList save(@RequestBody ConnectionSongPlayListVO connectionSongPlayListVO){
        ErrorEnumForConSongPlayList e;
        e=server.save(connectionSongPlayListVO);
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
    @PostMapping("/getConnectionByPlayListId")
    public List<ConnectionSongPlayListVO> getConnectionByPlayListId(@RequestBody PlayListVO playListVO){
        List<ConnectionSongPlayListVO>list;
        list=server.getConnectionByPlayListId(playListVO);
        return list;
    }
}