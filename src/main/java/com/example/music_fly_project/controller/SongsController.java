package com.example.music_fly_project.controller;
import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.server.SongsServer;
import com.example.music_fly_project.vo.SongsVO;
import com.example.music_fly_project.vo.TwoSongsVO;
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
    public ErrorsEnumForSongs save(@RequestBody SongsVO songsVO){
        return songsServer.save(songsVO);
    }
    @DeleteMapping("/delete")
    public ErrorsEnumForSongs delete(@RequestBody SongsVO songsVO){
        return songsServer.delete(songsVO.getId());
    }
    @PutMapping("/update")
    public ErrorsEnumForSongs update(@RequestBody SongsVO songsVO){
        return songsServer.update(songsVO);
    }
    @GetMapping("/checkCopyright")
    public ErrorsEnumForSongs checkCopyright(@RequestBody TwoSongsVO twoSongsVO){
        return songsServer.checkCopyright(twoSongsVO.getSong1(),twoSongsVO.getSong2());
    }

}
