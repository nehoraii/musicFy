package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import com.example.music_fly_project.server.AlbumsServer;
import com.example.music_fly_project.vo.AlbumsVO;
import com.example.music_fly_project.vo.SongsVO;
import com.example.music_fly_project.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/Albums")
@CrossOrigin(origins = "*")
//מחלקה האחרית לניתוב בקשות הקליינט על ידי ה-URL שאיתו מגיעה ושלחת את בקשתו לפונקציה המתאימה
public class AlbumsController {
    @Autowired
    private  AlbumsServer albumsServer;//אובייקט הכלה של albumsServer.

    /*
    מקבלת: אובייקט המייצג אלבום.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: מחזירה את האובייקט ובתוכו שדה אם השמיר ה עברה בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    @PostMapping("/save")
    public AlbumsVO save(@RequestBody AlbumsVO albumsVO){
        AlbumsVO albumsVORet;
        albumsVORet= albumsServer.save(albumsVO);
        return albumsVORet;
    }

    /*
    מקבלת: אובייקט המייצג אלבום.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: מחזירה האם הצליחה למחוק את האובייקט בהצלחה במידה ולא מחזירה את סיבת הבעיה.

    */
    @DeleteMapping("/delete")
    public ErrorsEnumForAlbums delete(@RequestBody AlbumsVO albumsVO){
        ErrorsEnumForAlbums e;
        e=albumsServer.delete(albumsVO.getId());
        return e;
    }
    /*@PutMapping("/update")
    public ErrorsEnumForAlbums update(@RequestBody AlbumsVO albumsVO){
        return albumsServer.update(albumsVO);
    }
     */


    /*
    מקבלת: אובייקט המייצג אלבום.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: רשימה של אובייקטים המייצגים שירים אשר נמצאים באלבום.
    */
    @PostMapping("/getSongsInAlbum")
    public List<SongsVO> getSongsInAlbum(@RequestBody AlbumsVO albumsVO){
        List<SongsVO>list;
        list=albumsServer.getSongsInAlbum(albumsVO.getId());
        return list;
    }

    /*
    מקבלת: אובייקט המייצג משתמש.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: רשימה של אובייקטים המייצגים שירים אשר שייכים למשתמש.
    */
    @PostMapping("/getAlbumsOfUser")
    public List<AlbumsVO> getAlbumsOfUser(@RequestBody UserVO userVO){
        List<AlbumsVO>list;
        list=albumsServer.getAlbumOfUser(userVO.getId());
        return list;
    }

    /*
    מקבלת: אובייקט המייצג אלבום.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: מערך של בייתים אשר מייצגים את תמונת האלבום.
    */
    @PostMapping("/getImageAlbumById")
    public byte[] getImageAlbum(@RequestBody AlbumsVO albumsVO){
        AlbumsVO albumsVO1;
        albumsVO1=albumsServer.getAlbumById(albumsVO.getId());
        return albumsVO1.getImageAlbum();
    }


    /*
    מקבלת: אובייקט המייצג אלבום.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: רשימה של המזהים הייחודים של השירים שנמצאים בתוך האלבום.
    */
    @PostMapping("/getSongsIdByAlbumId")
    public List<Long> getSongsIdByAlbumId(@RequestBody AlbumsVO albumsVO){
        List<Long> listId;
        listId=albumsServer.getSongsIdByAlbumId(albumsVO.getId());
        return listId;
    }


    /*
    מקבלת: אובייקט המייצג אלבום.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
   מחזירה: רשימה של אובייקטים המייצגים אלבום.
    */
    @PostMapping("/getAlbumsByName")
    public List<AlbumsVO> getAlbumsByName(@RequestBody AlbumsVO albumsVO){
        List<AlbumsVO> listVo;
        listVo=albumsServer.getAlbumsByName(albumsVO);
        return listVo;
    }

}
