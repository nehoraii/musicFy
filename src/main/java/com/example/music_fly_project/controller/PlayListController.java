package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorsEnumForPlayList;
import com.example.music_fly_project.server.PlayListServer;
import com.example.music_fly_project.vo.PlayListVO;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/PlayList")
@CrossOrigin(origins = "*")
public class PlayListController {
    @Autowired
    private PlayListServer playListServer;
    @PostMapping("/save")
    public PlayListVO save(@RequestBody PlayListVO playListVO){
        return playListServer.save(playListVO);
    }
    @DeleteMapping("/delete")
    public ErrorsEnumForPlayList delete(@RequestBody PlayListVO playListVO){
        ErrorsEnumForPlayList e;
        e= playListServer.delete(playListVO.getId());
        return e;
    }
    /*@PutMapping("/update")
    public ErrorsEnumForPlayList update(@RequestBody PlayListVO playListVO){
        ErrorsEnumForPlayList e;
        e=playListServer.update(playListVO);
        return e;
    }

     */
    @PostMapping("getPlayListByName")
    public List<PlayListVO> getPlayListByName(@RequestBody PlayListVO playListVO){
        List<PlayListVO> list;
        list=playListServer.getPlayListByName(playListVO);
        return list;
    }
    @PostMapping("/getPlayListByUserId")
    public List<PlayListVO> getPlayListByUserId(@RequestBody PlayListVO playListVO) {
        List<PlayListVO>list;
        list=playListServer.getPlayListByUserId(playListVO);
        return list;
    }
    @PostMapping("/changeImage")
    public void changeImage(@RequestBody PlayListVO playListVO){
        playListServer.changeImagePlayList(playListVO.getId());
    }
    @PostMapping("/getPlayListByPlayListId")
    public List<Long> getSongsIdByPlayListId(@RequestBody PlayListVO playListVO){
        List<Long>listId;
        listId=playListServer.getPlayListById(playListVO);
        return listId;
    }
    @PostMapping("/getInfoPlayList")
    public List<SongsVO> getInfo(@RequestBody PlayListVO playListVO){
        List<SongsVO> listVo;
        listVo=playListServer.getPlayListInfoById(playListVO);
        return listVo;
    }


}
