package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.logic.FtpLogic;
import com.example.music_fly_project.logic.Security;
import com.example.music_fly_project.logic.SongsLogic;
import com.example.music_fly_project.repository.SongsRepository;
import com.example.music_fly_project.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SongsServer {
    @Autowired
    private SongsRepository songsRepository;//אובייקט הכלה מסוג SongsRepository.
    @Autowired
    private ConnectionSongAlbumServer connectionSongAlbumServer;//אובייקט הכלה מסוג ConnectionSongAlbumServer.
    @Autowired
    private ConnectionSongPlayListServer connectionSongPlayListServer;//אובייקט הכלה מסוג ConnectionSongPlayListServer.
    private int limitToSearch=5;//מספר השירים שיופיעו בעת חיפוש.



    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: שמירת השיר במסד הנתונים ופתיחת תיקייה בשבילו במסד בשרת ה-FTP ושמירת הצ'אנק הראשון.
    מחזירה: אובייקט המייצג שיר ובתוכו את השדות הרלוונטיים למשתמש.
    */
    public SongsVO save(SongsVO songsVO) {
        Security.decipherFromClient(songsVO);
        if(songsVO.getId()==-1){
            long songId=saveToDB(songsVO);
            songsVO.setId(songId);
            boolean sec;
            ErrorsEnumForSongs e=ErrorsEnumForSongs.GOOD;
            sec=FtpLogic.createDirectory(songId,songsVO.getUserId(),songsVO.getUserId());
            if(sec){
                String path=FtpLogic.getPath()+"\\"+songsVO.getUserId()+"\\"+songId;
                e=update(songsVO,path);
            }
            songsVO.setE(e);
        }
        boolean ans=saveFtp(songsVO,songsVO.getChunkNum());
        if(ans){
            songsVO.setE(ErrorsEnumForSongs.GOOD);
        }
        songsVO.setNameSong(null);
        songsVO.setDate(null);
        songsVO.setZaner(null);
        songsVO.setUserId(null);
        //הקליינט לא צריך את הנתונים הנ"ל בעת הוספת שיר למערכת
        return songsVO;
    }


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: שמירת האובייקט במסד המידע.
    מחזירה: המזהה הייחודי של השיר החדש.
    */
    private Long saveToDB(SongsVO songsVO){
        Security.encodeToDB(songsVO);
        SongsEntity songsEntity=new SongsEntity();
        BeanUtils.copyProperties(songsVO,songsEntity);
        songsEntity=songsRepository.save(songsEntity);
        Security.decipherFromDB(songsVO);
        return songsEntity.getId();
    }


    /*
    מקבלת: אובייקט המייצג שיר, את מספר הצ'אנק.
    מבצעת: שמירת הצ'אנק בשרת ה-FTP
    מחזירה: האם הצליחה לשמור או לא.
    */
    private boolean saveFtp(SongsVO songsVO,int chuckId) {
        Security.encodeToDB(songsVO);
        boolean sec=FtpLogic.uploadFile(songsVO.getUserId(),songsVO.getUserId(),songsVO.getId(),songsVO.getTheSong(), Long.valueOf(chuckId));
        Security.decipherFromDB(songsVO);
        return sec;
    }


    /*
    מקבלת: המזהה הייחודי של השיר.
    מבצעת: מחיקת השיר ממסד הנתונים ומהשרת FTP.
    מחזירה: האם הצליחה למחוק בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    public ErrorsEnumForSongs delete(Long id){
        Optional<SongsEntity> songE=geyById(id);
        if(!songE.isPresent()){
            return ErrorsEnumForSongs.SONG_NOT_FOUND;
        }
        SongsEntity s=getSongById(id);
        FtpLogic.deleteFile(s.getSongPath(),s.getUserId(),s.getUserId());
        connectionSongAlbumServer.delConBySongId(id);
        connectionSongPlayListServer.delCon(id);
        songsRepository.deleteById(id);
        return ErrorsEnumForSongs.GOOD;
    }


    /*
    מקבלת: אובייקט המייצג שיר, ונתיב חדש בשרת ה-FTP לשיר.
    מבצעת: עדכון השיר במסד הנתונים והוספת הנתיב.
    מחזירה: האם הצליחה לעדכן במידה ולא מחזירה את סיבת הבעיה.
    */
    private ErrorsEnumForSongs update(SongsVO songsVO,String path) {
        Optional<SongsEntity> songE=geyById(songsVO.getId());
        if(!songE.isPresent()){
            return ErrorsEnumForSongs.SONG_NOT_FOUND;
        }
        SongsEntity bean= new SongsEntity();
        bean.setSongPath(path);
        Security.encodeToDB(songsVO);
        BeanUtils.copyProperties(songsVO,bean);
        songsRepository.save(bean);
        Security.decipherFromDB(songsVO);
        return ErrorsEnumForSongs.GOOD;
    }



    /*
    מקבלת: המזהה הייחודי לש השיר.
    מבצעת: מביאה את המידע ממסד הנתונים.
    מחזירה: מחזירה אובייקט המייצג את השיר.
    */
    private Optional<SongsEntity> geyById(Long id){
        Optional<SongsEntity> user=songsRepository.findById(id);
        return user;
    }


    /*
    מקבלת: המזהה הייחודי של השיר.
    מבצעת: מביאה אובייקט המייצג מידע על השיר.
    מחזירה: מחזירה את האובייקט.
    */
    private SongsEntity getSongById(Long songId){
        SongsEntity song;
        song =songsRepository.getReferenceById(songId);
        return song;
    }
   /* public SongsVO getSongById(SongsVO songsVO){
        SongsEntity songsEntity=getSongById(songsVO.getId());
        BeanUtils.copyProperties(songsEntity,songsVO);
        //Security.decipherFromDB(songsVO);
        //byte arr[]=FtpLogic.requestFileFromServer(addressFtp,portFtr,-1,-1,Long.toString(songsVO.getUserId())+"\\"+Long.toString(songsVO.getId()));
        byte arr[]=FtpLogic.requestFileFromServer(songsVO.getSongPath());
        songsVO.setTheSong(arr);
       // Security.decipherFromDB(songsVO);
        //Security.encodeToClient(songsVO);
        return songsVO;
    }
    */


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: מביאה את המידע על השיר.
    מחזירה: מחזירה אובייקט המייצג את השיר ובתוכו את המידע הרלוונטי למשתמש.
    */
    public SongsVO getSongProperty(SongsVO songsVO) {
        SongsEntity songsEntity = getSongById(songsVO.getId());
        BeanUtils.copyProperties(songsEntity,songsVO);
        Security.decipherFromDB(songsVO);
        int amountOfFiles=FtpLogic.getAmountOfFiles(songsEntity.getSongPath());
        songsVO.setAmountOfChunks(amountOfFiles);
        Security.encodeToClient(songsVO);
        return songsVO;
    }


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: מביאה את הצ'אנק המתאים לשיר המתאים.
    מחזירה: אוביקט המייצג שיר ובתוכו המידע הרלוונטי לקליינט.
    */
    public SongsVO getChunkId(SongsVO songsVO) {
        SongsEntity s = getSongById(songsVO.getId());
        byte arr[];
        arr = FtpLogic.requestFileFromServer(s.getSongPath()+"\\"+songsVO.getChunkNum());
        songsVO.setTheSong(arr);
        Security.decipherFromDB(songsVO);
        Security.encodeToClient(songsVO);
        return songsVO;
    }


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: מביאה את השירים מהמסד מידע שיש להם ששווים /דומים בשם.
   מחזירה: מחזירה רשימה של אובייקטים המייצגים שיר.
    */
    public List<SongsVO> getSongByName(SongsVO songsVO){
        Optional<List<SongsEntity>> songsEntity;
        try {
            Security.decipherFromClient(songsVO);
            Security.encodeToDB(songsVO);
            System.out.println(songsVO.getNameSong());
            String name=songsVO.getNameSong();
            songsEntity=songsRepository.getSongByName(name, songsVO.getId(),limitToSearch);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        if(!songsEntity.isPresent()||songsEntity.get().size()==0){
            return null;
        }
        List<SongsVO>listVo;
        listVo=SongsLogic.copyListEntityToVO(songsEntity.get());
        for (int i = 0; i < listVo.size(); i++) {
            Security.decipherFromDB(listVo.get(i));
            Security.encodeToClient(listVo.get(i));
        }
        return listVo;
    }


    /*
    מקבלת: המזהה הייחודי של השיר.
    מבצעת: מביאה את ממסד הנתונים את התמונה של האלבום שממנו נלקח השיר.
    מחזירה: מערך של בייתים המייצג את התמונה.
    */
    public byte[] getImageSong(Long songId){
        Optional<AlbumsEntity> albums;
        albums=songsRepository.getAlbumBySongId(songId);
        if(!albums.isPresent()){
            return null;
        }
        byte[] image=albums.get().getImageAlbum();
        return image;
    }
}
