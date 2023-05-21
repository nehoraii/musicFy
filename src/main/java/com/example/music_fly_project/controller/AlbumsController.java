package com.example.music_fly_project.controller;

import com.example.music_fly_project.repository.AlbumsRepository;
import com.example.music_fly_project.server.AlbumsServer;
import com.example.music_fly_project.vo.AlbumsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/Albums")
public class AlbumsController {
    @Autowired
    private  AlbumsServer albumsServer;
    @PostMapping("/save")
    public long save(@RequestBody AlbumsVO albumsVO){
        return albumsServer.save(albumsVO);
    }
    @DeleteMapping("/delete")
    public long delete(@RequestBody AlbumsVO albumsVO){
        return albumsServer.delete(albumsVO.getId());
    }
    @PutMapping("/update")
    public long update(@RequestBody AlbumsVO albumsVO){
        long userId=albumsServer.update(albumsVO);
        return userId;
    }
}
