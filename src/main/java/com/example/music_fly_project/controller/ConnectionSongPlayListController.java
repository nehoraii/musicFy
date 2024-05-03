package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorEnumForConSongPlayList;
import com.example.music_fly_project.server.ConnectionSongPlayListServer;
import com.example.music_fly_project.vo.ConnectionSongPlayListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RequestMapping("/ConSongPlayList")
@RestController
@CrossOrigin("*")
//מחלקה האחרית לניתוב בקשות הקליינט על ידי ה-URL שאיתו מגיעה ושלחת את בקשתו לפונקציה המתאימה
public class ConnectionSongPlayListController {
    @Autowired
    private ConnectionSongPlayListServer server;//אובייקט הכלה ל- ConnectionSongPlayListServer.


    /*
    מקבלת: אובייקט המייצג חיבור בין פלייליסט לשיר.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: האם הצליחה לשמור את הקשר בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    @PostMapping("/save")
    public ErrorEnumForConSongPlayList save(@RequestBody ConnectionSongPlayListVO connectionSongPlayListVO){
        ErrorEnumForConSongPlayList e;
        e=server.save(connectionSongPlayListVO);
        return e;
    }


    /*
    מקבלת: אובייקט המייצג חיבור בין פלייליסט לשיר.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: האם הצליחה למחוק את הקשר בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    @DeleteMapping("/delete")
    public ErrorEnumForConSongPlayList delete(@RequestBody ConnectionSongPlayListVO connectionSongPlayListVO){
        ErrorEnumForConSongPlayList e;
        e=server.delete(connectionSongPlayListVO);
        return e;
    }
    /*
    @PutMapping("/update")
    public ErrorEnumForConSongPlayList update(@RequestBody twoConnectionSongPlayListVO con){
        ErrorEnumForConSongPlayList e;
        e=server.update(con);
        return e;
    }
    */
}
