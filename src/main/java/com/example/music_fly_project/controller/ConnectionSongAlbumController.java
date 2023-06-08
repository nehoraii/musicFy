package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import com.example.music_fly_project.enums.ErrosEnumForConnectionSongAlbum;
import com.example.music_fly_project.server.ConnectionSongAlbumServer;
import com.example.music_fly_project.vo.AlbumsVO;
import com.example.music_fly_project.vo.ConnectionSongAlbumsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/ConSongAlbum")
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
    @GetMapping("/get")
    public List<ConnectionSongAlbumsVO> getAlbum(@RequestBody AlbumsVO albumsVO){
        ErrosEnumForConnectionSongAlbum e;
        e=connectionSongAlbumServer.FileTheList(albumsVO.getId());
        if(e!=ErrosEnumForConnectionSongAlbum.GOOD){
            System.out.println(e);
            return null;
        }
        List<ConnectionSongAlbumsVO>albums;
        albums=connectionSongAlbumServer.getListAlbum();
        return albums;
    }
}
