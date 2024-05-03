package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrosEnumForConnectionSongAlbum;
import com.example.music_fly_project.server.ConnectionSongAlbumServer;
import com.example.music_fly_project.server.SongsServer;
import com.example.music_fly_project.vo.ConnectionSongAlbumsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("/ConSongAlbum")
@CrossOrigin("*")
//מחלקה האחרית לניתוב בקשות הקליינט על ידי ה-URL שאיתו מגיעה ושלחת את בקשתו לפונקציה המתאימה
public class ConnectionSongAlbumController {
    @Autowired
    private ConnectionSongAlbumServer connectionSongAlbumServer;//אובייקט הכלה של connectionSongAlbumServer.
    @Autowired
    private SongsServer songsServer; //אובייקט הכלה של songsServer.



    /*
    מקבלת: אובייקט המייצג חיבור בין אלבום לשיר.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: האם הצליחה לשמור את הקשר בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    @PostMapping("/save")
    public ErrosEnumForConnectionSongAlbum save(@RequestBody ConnectionSongAlbumsVO conVO){
        ErrosEnumForConnectionSongAlbum e;
        e=connectionSongAlbumServer.save(conVO);
        return e;
    }
   /* @PutMapping("/update")
    public ErrosEnumForConnectionSongAlbum update(@RequestBody ConnectionSongAlbumsVO conVO){
        ErrosEnumForConnectionSongAlbum e;
        e=connectionSongAlbumServer.update(conVO);
        return e;
    }
    */


    /*מקבלת: אובייקט המייצג חיבור בין אלבום לשיר.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: האם הצליחה למחוק את הקשר בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    @DeleteMapping("/delete")
    public ErrosEnumForConnectionSongAlbum delete(@RequestBody ConnectionSongAlbumsVO conVO){
        ErrosEnumForConnectionSongAlbum e;
        e=connectionSongAlbumServer.delConBySongId(conVO.getSongId());
        songsServer.delete(conVO.getSongId());
        return e;
    }
   /* @PostMapping("/getConnectionByAlbumId")
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
    */
}
