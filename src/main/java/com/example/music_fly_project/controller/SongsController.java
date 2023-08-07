package com.example.music_fly_project.controller;
import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.server.SongsServer;
import com.example.music_fly_project.vo.SongsVO;
import com.example.music_fly_project.vo.TwoIdSongs;
import com.example.music_fly_project.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    @PutMapping("/update")
    public ErrorsEnumForSongs update(@RequestBody SongsVO songsVO){
        ErrorsEnumForSongs e;
        e=songsServer.update(songsVO);
        return e;
    }
    /*@PostMapping("/checkCopyright")
    public ErrorsEnumForSongs checkCopyright(@RequestBody TwoSongsVO twoSongsVO){
        ErrorsEnumForSongs e;
        e=songsServer.checkCopyright(twoSongsVO.getSong1(),twoSongsVO.getSong2());
        return e;
    }
     */
    @PostMapping(value = "/getSongById",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getTheSongById(@RequestBody SongsVO songsVO){
        SongsVO song=songsServer.getSongById(songsVO);
        return song.getTheSong();
    }
    @PostMapping("/getSongByName")
    public List<SongsVO> getSongByName(@RequestBody SongsVO songsVO){
        List<SongsVO> list=songsServer.getSongByName(songsVO);
        return list;
    }
    @PostMapping("/getSongsByUserId")
    public List<SongsVO> getSongsByUserId(@RequestBody UserVO userVO){
        List<SongsVO> list=songsServer.getSongsByUserId(userVO);
        return list;
    }
    @PostMapping("/getCopyright")
    public ErrorsEnumForSongs getCopyright (@RequestBody TwoIdSongs twoIdSongs){
        ErrorsEnumForSongs e;
        e=songsServer.getCopyright(twoIdSongs);
        return e;
    }
    @PostMapping("/getImageSong")
    public byte[] getImageSong(@RequestBody SongsVO songsVO){
        byte[] image;
        image=songsServer.getImageSong(songsVO.getId());
        return image;
    }
    @GetMapping("/try")
    public String check(){
        TwoIdSongs twoIdSongs=new TwoIdSongs();
        twoIdSongs.setSourceId(86L);
        twoIdSongs.setImitationId(85L);
        return songsServer.getCopyright(twoIdSongs).name();
    }
}
