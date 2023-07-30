package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import com.example.music_fly_project.repository.AlbumsRepository;
import com.example.music_fly_project.server.AlbumsServer;
import com.example.music_fly_project.vo.AlbumsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/Albums")
@CrossOrigin(origins = "*")
public class AlbumsController {
    @Autowired
    private  AlbumsServer albumsServer;
    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE    )
    public AlbumsVO save(@RequestBody AlbumsVO albumsVO){
        return albumsServer.save(albumsVO);
    }
    @DeleteMapping("/delete")
    public ErrorsEnumForAlbums delete(@RequestBody AlbumsVO albumsVO){
        return albumsServer.delete(albumsVO.getId());
    }
    @PutMapping("/update")
    public ErrorsEnumForAlbums update(@RequestBody AlbumsVO albumsVO){
        return albumsServer.update(albumsVO);
    }
}
