package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForAlbums;
import com.example.music_fly_project.logic.AlbumsLogic;
import com.example.music_fly_project.logic.FtpLogic;
import com.example.music_fly_project.logic.Security;
import com.example.music_fly_project.repository.AlbumsRepository;
import com.example.music_fly_project.vo.AlbumsVO;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumsServer {
    @Autowired
    private AlbumsRepository albumsRepository;//אובייקט הכלה מסוג AlbumsRepository.
    private int limitAlbumsSearch=5;//כמות האובייקטים שתוחזר בחיפוש אלבום על פי שם.


    /*
    מקבלת: אובייקט מסוג AlbumsVO.
    מבצעת: שומרת את הנתונים של האלבום במסד הנתונים.
    מחזירה: אובייקט של האלבום ובתוכו את השדות הרלוונטיים למשתמש.
    */
    public AlbumsVO save(AlbumsVO albumsVO){
        thereIsDirectory(albumsVO.getUserId());
        AlbumsEntity bean =new AlbumsEntity();
        BeanUtils.copyProperties(albumsVO,bean);
        try {
            bean=albumsRepository.save(bean);
            BeanUtils.copyProperties(bean,albumsVO);
        }catch (Exception e){
            System.out.println(e);
            albumsVO.setE(ErrorsEnumForAlbums.NOT_SAVED_SUCCESSFULLY);
            return albumsVO;
        }
        albumsVO.setE(ErrorsEnumForAlbums.GOOD);
        albumsVO.setImageAlbum(null);
        albumsVO.setNameAlbum(null);
        albumsVO.setLengthAlbum(-1);
        albumsVO.setUserId(null);
        return albumsVO;
    }


    /*
    מקבלת: המזהה הייחודי של המשתמש.
    מבצעת: בודקת האם יש לו תיקייה בשרת ה-FTP במידה ולא מייצרת לו תיקייה.
    מחזירה: כלום.
    */
    private void thereIsDirectory(Long userId){
        //בדיקה אם יש למשתמש לפחות שיר אחד אם יש לו לפחות שיר אחד זה אומר שיש לו תיקייה ושלא צריך ליצור לו תיקייה אם אין לו זה אומר שצריך ליצור לו תיקייה
        //מעדיף לבצע שאילתה עבור כל אלבום מאשר שסתם יהיה לכל משתמש תיקייה
        Optional<AlbumsEntity>s=albumsRepository.getAlbumByUserIdLIMIT(userId);
        if(s.isPresent()==false){
            FtpLogic.createDirectory(userId,userId);
        }
    }


    /*
    מקבלת: המזהה הייחודי של האלבום.
מבצעת: מוחקת את האובייקט מהטבלה.
מחזירה: האם הצליחה למחוק בהצלחה במידה ולא את סיבת הבעיה.
    */
    public ErrorsEnumForAlbums delete(Long id){
        Optional<AlbumsEntity> albums;
        albums=getById(id);
        if(!albums.isPresent()){
            return ErrorsEnumForAlbums.ALBUMS_NOT_FOUND;
        }
        Optional<List<Object[]>> songsPath=albumsRepository.getSongsPath(id);
        if(songsPath.isPresent()){
            for (int i = 0; i < songsPath.get().size(); i++) {
                FtpLogic.deleteFile((String) songsPath.get().get(i)[0],(Long)songsPath.get().get(i)[1],(Long)songsPath.get().get(i)[1]);
            }
        }
        albumsRepository.delAllConPlayList(id);
        albumsRepository.delAllSong(id);
        albumsRepository.delAllConAlbumSong(id);
        albumsRepository.deleteById(id);
        return ErrorsEnumForAlbums.GOOD;
    }
    /*public ErrorsEnumForAlbums update(AlbumsVO albumsVO){
        Optional<AlbumsEntity> albums;
        albums=getById(albumsVO.getId());
        if(!albums.isPresent()){
            return ErrorsEnumForAlbums.AlbumsNotFound;
        }
        AlbumsEntity bean=new AlbumsEntity();
        BeanUtils.copyProperties(albumsVO,bean);
        albumsRepository.save(bean);
        return ErrorsEnumForAlbums.GOOD;
    }

     */


    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: מביאה אובייקט המייצג את האלבום ממסד הנתונים.
    מחזירה: מחזירה את האובייקט.
    */
    private Optional<AlbumsEntity> getById(Long id){
        Optional<AlbumsEntity> albumsEntity;
        albumsEntity=albumsRepository.findById(id);
        return albumsEntity;
    }



    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: מביאה את רשימת השירים אשר נמצאים באלבום.
   מחזירה: מחזירה רשימה של אובייקטים המייצגים שיר.
    */
    public List<SongsVO> getSongsInAlbum(Long albumId){
        Optional<List<SongsEntity>> list;
        list=albumsRepository.getAlbumsSongByAlbumId(albumId);
        if(!list.isPresent()){
            return null;
        }
        List<SongsVO> listId=new ArrayList<>();
        for (int i = 0; i < list.get().size(); i++) {
            SongsVO songsVO=new SongsVO();
            BeanUtils.copyProperties(list.get().get(i),songsVO);
            Security.decipherFromDB(songsVO);
            Security.encodeToClient(songsVO);
            listId.add(songsVO);
        }
        return listId;
    }


    /*
    מקבלת: המזהה הייחודי של המשתמש.
    מבצעת: מביאה את כל האלבומים של המשתמש.
    מחזירה: מחזירה את רשימה של אובייקטים המייצגים אלבום.
    */
    public List<AlbumsVO> getAlbumOfUser(Long userId){
        Optional<List<AlbumsEntity>> list;
        list=albumsRepository.getAlbumsOfUser(userId);
        if(!list.isPresent()){
            return null;
        }
        List listVo=new ArrayList<>();
        int lenAlbum;
        for (int i = 0; i < list.get().size(); i++) {
            AlbumsVO albumsVO=new AlbumsVO();
            AlbumsLogic.copyProperty(list.get().get(i),albumsVO);
            lenAlbum=getLengthAlbum(albumsVO.getId());
            albumsVO.setLengthAlbum(lenAlbum);
            listVo.add(albumsVO);
        }
        return listVo;
    }


    /*
    מקבלת: המזהה הייחודי של האלבום.
מבצעת: בודקת כמה שירים יש באלבום.
    מחזירה: מחזירה את כמות השירים שנמצאת באלבום.
    */
    private int getLengthAlbum(Long albumId){
        int lengthAlbum;
        lengthAlbum=albumsRepository.getLengthAlbum(albumId);
        return lengthAlbum;
    }


    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: מביאה אובייקט המייצג את האלבום ממסד הנתונים.
    מחזירה: מחזירה את האובייקט.
    */
    public AlbumsVO getAlbumById(Long albumId){
        Optional<AlbumsEntity> albumEntity;
        albumEntity=getById(albumId);
        if(!albumEntity.isPresent()){
            return null;
        }
        AlbumsVO albumsVO=new AlbumsVO();
        AlbumsLogic.copyProperty(albumEntity.get(),albumsVO);
        return albumsVO;
    }


    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: מביאה את המזהים הייחודים של השירים אשר נמצאים באלבום.
    מחזירה: רשימה של המזהים.
    */
    public List<Long> getSongsIdByAlbumId(Long albumId){
        Optional<List<Object>> list;
        list=albumsRepository.getAlbumsSongIdByAlbumId(albumId);
        if(!list.isPresent()){
            return null;
        }
        List<Long> listId=new ArrayList<>();
        Long someId;
        for (int i = 0; i < list.get().size(); i++) {
            someId=(Long) list.get().get(i);
            listId.add(someId);
        }
        return listId;
    }


    /*
    מקבלת: אובייקט המייצג אלבום.
    מבצעת: מביאה את רשימת האלבומים שיש להם שם דומה או שווה לשם שקיבלנו באובייקט.
    מחזירה: מחזירה רשימה של אובייקטים המייצגים אלבום.
    */
    public List<AlbumsVO> getAlbumsByName(AlbumsVO albumsVO){
        Optional<List<AlbumsEntity>>listEntity;
        listEntity=albumsRepository.getAlbumsByName(albumsVO.getNameAlbum(), albumsVO.getId(), limitAlbumsSearch);
        if(!listEntity.isPresent()){
            return null;
        }
        List<AlbumsVO>listVo=new ArrayList<>();
        int lenAlbum;
        for (int i = 0; i < listEntity.get().size(); i++) {
            AlbumsVO someAlbum=new AlbumsVO();
            BeanUtils.copyProperties(listEntity.get().get(i),someAlbum);
            lenAlbum=getLengthAlbum(someAlbum.getId());
            someAlbum.setLengthAlbum(lenAlbum);
            listVo.add(someAlbum);
        }
        return listVo;
    }
}
