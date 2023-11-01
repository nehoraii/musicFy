package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import com.example.music_fly_project.repository.AlbumsRepository;
import com.example.music_fly_project.server.AlbumsServer;
import com.example.music_fly_project.vo.AlbumsVO;
import com.example.music_fly_project.vo.SongsVO;
import com.example.music_fly_project.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/getSongsInAlbum")
    public List<SongsVO> getSongsInAlbum(@RequestBody AlbumsVO albumsVO){
        List<SongsVO>list;
        list=albumsServer.getSongsInAlbum(albumsVO.getId());
        return list;
    }
    @PostMapping("/getAlbumOfUser")
    public List<AlbumsVO> getAlbumOfUser(@RequestBody UserVO userVO){
        List<AlbumsVO>list;
        list=albumsServer.getAlbumOfUser(userVO.getId());
        return list;
    }
    @PostMapping("/getImageAlbumById")
    public byte[] getImageAlbum(@RequestBody AlbumsVO albumsVO){
        AlbumsVO albumsVO1;
        albumsVO1=albumsServer.getAlbumById(albumsVO.getId());
        return albumsVO1.getImageAlbum();
    }
    @PostMapping("/getSongsIdByAlbumId")
    public List<Long> getSongsIdByAlbumId(@RequestBody AlbumsVO albumsVO){
        List<Long> listId;
        listId=albumsServer.getSongsIdByAlbumId(albumsVO.getId());
        return listId;
    }
    @PostMapping("/getAlbumsByName")
    public List<AlbumsVO> getAlbumsByName(@RequestBody AlbumsVO albumsVO){
        List<AlbumsVO> listVo;
        listVo=albumsServer.getAlbumsByName(albumsVO);
        return listVo;
    }

}
