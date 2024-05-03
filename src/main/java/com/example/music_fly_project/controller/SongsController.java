package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.server.SongsServer;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/Song")
@CrossOrigin(origins = "*")
//מחלקה האחרית לניתוב בקשות הקליינט על ידי ה-URL שאיתו מגיעה ושלחת את בקשתו לפונקציה המתאימה
public class SongsController {
    @Autowired
    private SongsServer songsServer;//אובייקט הכלה מסוג SongsServer.


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: אובייקט המייצג את השיר ובתוכו שדה שבו יהיה את המצב האם הצלחנו לשמור את האובייקט בהצלחה במידה ולא תוחזר סיבת הבעיה.
    */
    @PostMapping("/save")
    public SongsVO save(@RequestBody SongsVO songsVO){
        SongsVO e;
        e= songsServer.save(songsVO);
        return e;
    }


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: האם הצלחנו למחוק את האובייקט בהצלחה במידה ולא תוחזר סיבת הבעיה.
    */
    @DeleteMapping("/delete")
    public ErrorsEnumForSongs delete(@RequestBody SongsVO songsVO){
        ErrorsEnumForSongs e;
        e=songsServer.delete(songsVO.getId());
        return e;
    }
    /*@PutMapping("/update")
    public ErrorsEnumForSongs update(@RequestBody SongsVO songsVO){
        ErrorsEnumForSongs e;
        e=songsServer.update(songsVO);
        return e;
    }
    */


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: מחזירה אובייקט המייצג שיר.
    */
    @PostMapping("/getProperties")
    public SongsVO getSongProperty(@RequestBody SongsVO songsVO){
        songsVO=songsServer.getSongProperty(songsVO);
        return songsVO;
    }



    @PostMapping("/getChunk")
    public SongsVO getTheSongById(@RequestBody SongsVO songsVO){
        SongsVO song=songsServer.getChunkId(songsVO);
        return song;
    }


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: רשימה של אובייקטים המייצגים שיר.
     */
    @PostMapping("/getSongByName")
    public List<SongsVO> getSongsByName(@RequestBody SongsVO songsVO){
        List<SongsVO> list=songsServer.getSongByName(songsVO);
        return list;
    }


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: מערך של הבייתים המייצג תמונה של האלבום שממנו בא השיר.
    */
    @PostMapping("/getImageSong")
    public byte[] getImageSong(@RequestBody SongsVO songsVO){
        byte[] image;
        image=songsServer.getImageSong(songsVO.getId());
        return image;
    }

}
