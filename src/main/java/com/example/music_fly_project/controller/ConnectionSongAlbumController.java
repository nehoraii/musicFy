package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import com.example.music_fly_project.enums.ErrosEnumForConnectionSongAlbum;
import com.example.music_fly_project.server.ConnectionSongAlbumServer;
import com.example.music_fly_project.vo.AlbumsVO;
import com.example.music_fly_project.vo.ConnectionSongAlbumsVO;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Validated
@RestController
@RequestMapping("/ConSongAlbum")
@CrossOrigin("*")
public class ConnectionSongAlbumController {
    @Autowired
    private ConnectionSongAlbumServer connectionSongAlbumServer;
    @PostMapping("/save")
    public ErrosEnumForConnectionSongAlbum save(@RequestBody ConnectionSongAlbumsVO conVO){
        ErrosEnumForConnectionSongAlbum e;
        e=connectionSongAlbumServer.save(conVO);
        return e;
    }
    @PutMapping("/update")
    public ErrosEnumForConnectionSongAlbum update(@RequestBody ConnectionSongAlbumsVO conVO){
        ErrosEnumForConnectionSongAlbum e;
        e=connectionSongAlbumServer.update(conVO);
        return e;
    }
    @DeleteMapping("/delte")
    public ErrosEnumForConnectionSongAlbum delete(@RequestBody ConnectionSongAlbumsVO conVO){
        ErrosEnumForConnectionSongAlbum e;
        e=connectionSongAlbumServer.delete(conVO.getId());
        return e;
    }
    @PostMapping("/getConnectionByAlbumId")
    public List<ConnectionSongAlbumsVO> getAlbum(@RequestBody AlbumsVO albumsVO){
        List<ConnectionSongAlbumsVO> album;
        album=connectionSongAlbumServer.getConnectionByAlbum(albumsVO.getId());
        return album;
    }
    @PostMapping("/getConnectionBySongId")
    public List<ConnectionSongAlbumsVO> getAlbumBySongId(@RequestBody SongsVO songsVO){
        List<ConnectionSongAlbumsVO>list;
        list=connectionSongAlbumServer.getConnectionBySongId(songsVO.getId());
        return list;
    }
}
