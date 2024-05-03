package com.example.music_fly_project.controller;

import com.example.music_fly_project.enums.ErrorsEnumForPlayList;
import com.example.music_fly_project.server.PlayListServer;
import com.example.music_fly_project.vo.PlayListVO;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/PlayList")
@CrossOrigin(origins = "*")
//מחלקה האחרית לניתוב בקשות הקליינט על ידי ה-URL שאיתו מגיעה ושלחת את בקשתו לפונקציה המתאימה
public class PlayListController {
    @Autowired
    private PlayListServer playListServer;//אובייקט הכלה של playListServer.


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: את האובייקט ובתוכו שדה שמכיל האם הצליחה לשמור את הפלייליסט בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    @PostMapping("/save")
    public PlayListVO save(@RequestBody PlayListVO playListVO){
        PlayListVO playListRet;
        playListRet=playListServer.save(playListVO);
        return playListRet;
    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: האם הצליחה למחוק את הפלייליסט בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    @DeleteMapping("/delete")
    public ErrorsEnumForPlayList delete(@RequestBody PlayListVO playListVO){
        ErrorsEnumForPlayList e;
        e= playListServer.delete(playListVO.getId());
        return e;
    }
    /*@PutMapping("/update")
    public ErrorsEnumForPlayList update(@RequestBody PlayListVO playListVO){
        ErrorsEnumForPlayList e;
        e=playListServer.update(playListVO);
        return e;
    }

     */


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: רשימה של אובייקטים המייצגים פלייליסט.
    */
    @PostMapping("getPlayListByName")
    public List<PlayListVO> getPlayListByName(@RequestBody PlayListVO playListVO){
        List<PlayListVO> list;
        list=playListServer.getPlayListByName(playListVO);
        return list;
    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: רשימה של אובייקטים המייצגים פלייליסט.
    */
    @PostMapping("/getPlayListByUserId")
    public List<PlayListVO> getPlayListByUserId(@RequestBody PlayListVO playListVO) {
        List<PlayListVO>list;
        list=playListServer.getPlayListByUserId(playListVO);
        return list;
    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: כלום.
    */
    @PostMapping("/changeImage")
    public void changeImage(@RequestBody PlayListVO playListVO){
        playListServer.changeImagePlayList(playListVO.getId());
    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: רשימה של המזהים הייחודים של שירים.
    */
    @PostMapping("/getPlayListByPlayListId")
    public List<Long> getSongsIdByPlayListId(@RequestBody PlayListVO playListVO){
        List<Long>listId;
        listId=playListServer.getPlayListById(playListVO);
        return listId;
    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: קוראת לפונקציה המתאימה ב-server ושולחת לו את האובייקט.
    מחזירה: רשימה של אובייקטים המייצגים שירים.
    */
    @PostMapping("/getInfoPlayList")
    public List<SongsVO> getInfo(@RequestBody PlayListVO playListVO){
        List<SongsVO> listVo;
        listVo=playListServer.getPlayListInfoById(playListVO);
        return listVo;
    }


}
