package com.example.music_fly_project.controller;
import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.logic.Security;
import com.example.music_fly_project.server.SongsServer;
import com.example.music_fly_project.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/Song")
@CrossOrigin(origins = "*")
public class SongsController {
    @Autowired
    private SongsServer songsServer;
    @PostMapping(value = "save2", consumes = "application/json")
    public SongsVO save2(
            @RequestHeader HttpHeaders headers,
            @RequestBody Map<String, Object> requestMap) {

        try {
            int currentChunk = Integer.parseInt(headers.getFirst("X-Chunk-Number"));
            int totalChunks = Integer.parseInt(headers.getFirst("X-Total-Chunks"));

            List<String> chunks = (List<String>) requestMap.get("chunks");
            StringBuilder stringBuilder = new StringBuilder();

            for (String chunk : chunks) {
                stringBuilder.append(chunk);
            }

            String jsonData = stringBuilder.toString();
            ObjectMapper objectMapper=new ObjectMapper();
            SongsVO s=objectMapper.readValue(jsonData,SongsVO.class);
            // לחזור עם התשובה המתאימה, לדוגמא:
            return s;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    @PostMapping(value = "/save")
    public SongsVO save(@RequestBody SongsVO songsVO){
        SongsVO e;
        e= songsServer.save(songsVO);
        return e;
    }
   /* @PostMapping(value = "/save")
    public SongsVO save2(@RequestBody SongsVO2 songsVO){
        SongsVO e;
        SongsVO s=new SongsVO();
        byte[] arr=new byte[songsVO.getTheSong().size()];
        for (int i = 0; i < songsVO.getTheSong().size(); i++) {
            arr[i]=songsVO.getTheSong().get(i);
        }
        System.out.println("e");
        e= songsServer.save(s);
        return e;
    }

    */
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
    @PostMapping("/getSongById")
    public SongsVO getTheSongById(@RequestBody SongsVO songsVO){
        SongsVO song=songsServer.getSongById(songsVO);
        return song;
    }
    @PostMapping("/getProperties")
    public SongVoController getSongProperty(@RequestBody SongVoController songVoController){
        songVoController=songsServer.getSongProperty(songVoController);
        return songVoController;
    }
    @PostMapping("/getChunk")
    public SongVoController getTheSongById2(@RequestBody SongVoController songsVO){
        SongVoController song=songsServer.getChunkId(songsVO);
        return song;
    }

    @PostMapping("/getSongByName")
    public List<SongsVO> getSongByName(@RequestBody SongsVO songsVO){
        List<SongsVO> list=songsServer.getSongByName(songsVO);
        return list;
    }
    @PostMapping("/getSongsByUserId")
    public List<SongsVO> getSongsByUserId(@RequestBody UserVO userVO) {
        List<SongsVO> list = songsServer.getSongsByUserId(userVO);
        return list;
    }
    @PostMapping("/getImageSong")
    public byte[] getImageSong(@RequestBody SongsVO songsVO){
        byte[] image;
        image=songsServer.getImageSong(songsVO.getId());
        return image;
    }

}
