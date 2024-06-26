package com.example.music_fly_project.server;

import java.io.*;

import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForPlayList;
import com.example.music_fly_project.logic.PlayListLogic;
import com.example.music_fly_project.logic.Security;
import com.example.music_fly_project.repository.PlayListRepository;
import com.example.music_fly_project.vo.PlayListVO;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayListServer {
    @Autowired
    private PlayListRepository playListRepository;//אובייקט הכלה מסוג PlayListRepository.
    private int limitPlayListSearch=5;//אורך האובייקטים שיחוזרו בעת החיפוש.


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: שומרת את האובייקט במסד הנתונים.
    מחזירה: את האובייקט ובתוכו את השדות הרלוונטיים למשתמש.
    */
    public PlayListVO save(PlayListVO playListVO){
        PlayListEntity bean= new PlayListEntity();
        playListVO.setDate(new Date());
        BeanUtils.copyProperties(playListVO,bean);
        bean=playListRepository.save(bean);
        BeanUtils.copyProperties(bean,playListVO);
        playListVO.setE(ErrorsEnumForPlayList.GOOD);
        return playListVO;
    }


    /*
    מקבלת: המזהה הייחודי של הפלייליסט.
    מבצעת: מוחקת את האובייקט ממסד הנתונים.
    מחזירה: האם הצליחה למחוק בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    public ErrorsEnumForPlayList delete(Long id){
        Optional<PlayListEntity>playList;
        playList=geyById(id);
        if(!playList.isPresent()){
            return ErrorsEnumForPlayList.PLAY_LIST_NOT_FOUND;
        }
        playListRepository.deleteById(id);
        playListRepository.delAllConByPlayListId(id);
        return ErrorsEnumForPlayList.GOOD;
    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: מעדכנת את השורה באובייקט המעודכן.
    מחזירה: האם הצליחה לעדכן בהצלחה במידה ולא מחזירה את סיבת הבעיה.
    */
    private ErrorsEnumForPlayList update(PlayListVO playListVO){
        Optional<PlayListEntity> bean;
        try{
            bean=geyById(playListVO.getId());
            if(!bean.isPresent()){
                return ErrorsEnumForPlayList.PLAY_LIST_NOT_FOUND;
            }
            BeanUtils.copyProperties(playListVO,bean.get());
            playListRepository.save(bean.get());
            return ErrorsEnumForPlayList.GOOD;
        }catch (Exception e){
            e.getMessage();
            return ErrorsEnumForPlayList.ELSE_ERROR;
        }
    }


    /*
    מקבלת: המזהה הייחודי של הפלייליסט.
    מבצעת: מחזירה אובייקט המייצג את הפלייליסט.
    מחזירה: מחזירה את האובייקט.
    */
    private  Optional<PlayListEntity> geyById(Long id){
        Optional<PlayListEntity> user=playListRepository.findById(id);
        return user;
    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: מביאה את הפלייליסטים שהשם שלהם כמו/דומה לשם שקיבלנו.
    מחזירה: רשימה של האובייקטים המייצגים את הפלייליסט.
    */
    public List<PlayListVO> getPlayListByName(PlayListVO playListVO) {
        Optional<List<PlayListEntity>>list;
            list=playListRepository.getPlayListByName(playListVO.getPlayListName(), playListVO.getId(),limitPlayListSearch);
        if(!list.isPresent()){
            return null;
        }
        List<PlayListVO> playListVO1;
        playListVO1=PlayListLogic.copyList(list.get());
        int lenPlayList;
        for (int i = 0; i < playListVO1.size(); i++) {
            lenPlayList=getLengthPlayList(playListVO1.get(i).getId());
            playListVO1.get(i).setLengthPlayList(lenPlayList);
        }
        return playListVO1;

    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: מביאה את הפלייליסטים של המשתמש.
    מחזירה: מחזירה רשימה של אובייקטים המייצגים פלייליסט.
    */
    public List<PlayListVO> getPlayListByUserId(PlayListVO playListVO){
        Optional<List<PlayListEntity>>list;
        list=playListRepository.getPlayListsByUserId(playListVO.getUserId());
        if(!list.isPresent()){
            return null;
        }
        PlayListLogic playListLogic=new PlayListLogic();
        List<PlayListVO> listVOS;
        listVOS=playListLogic.copyList(list.get());
        int lenPlayList;
        for (int i = 0; i < listVOS.size(); i++) {
            lenPlayList=getLengthPlayList(listVOS.get(i).getId());
            listVOS.get(i).setLengthPlayList(lenPlayList);
        }
        return listVOS;
    }


    /*
    מקבלת: מזהה ייחודי של פלייליסט.
    מבצעת: מביאה את מספר השירים שיש בו.
    מחזירה: מספר השירים שיש בו.
    */
    private int getLengthPlayList(Long playListId){
        int lengthPlayList;
        lengthPlayList=playListRepository.getLengthPlayList(playListId);
        return lengthPlayList;
    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: מביאה רשימה של המזהים הייחודים של השירים הנמצאים בפלייליסט.
    מחזירה: מחזירה רשימה של המייצגים הייחודים של השירים בפלייליסט.
    */
    public List<Long> getPlayListById(PlayListVO playListVO){
        Optional<List<Object>> list;
        list=playListRepository.getSongsIdByPlayListId(playListVO.getId());
        if(!list.isPresent()){
            return null;
        }
        List<Long> listId = new ArrayList<>();
        for (int i = 0; i < list.get().size(); i++) {
            listId.add((Long)list.get().get(i));
        }
        return listId;
    }


    /*
    מקבלת: אובייקט המייצג פלייליסט.
    מבצעת: מביאה את המידע על הפלייליסט.
    מחזירה: מחזירה אובייקט המייצג את הפלייליסט.
    */
    public List<SongsVO> getPlayListInfoById(PlayListVO playListVO){
        Optional<List<Object[]>> list;
        list=playListRepository.getPlayListInfoByPlayListId(playListVO.getId());
        if(!list.isPresent()){
            return null;
        }
        List<SongsVO> listVo = new ArrayList<>();
        for (int i = 0; i < list.get().size(); i++) {
            SongsVO songsVO=new SongsVO();
            songsVO.setId((Long) list.get().get(i)[0]);
            songsVO.setUserId((Long) list.get().get(i)[1]);
            songsVO.setZaner((String) list.get().get(i)[2]);
            songsVO.setDate((Date) list.get().get(i)[3]);
            songsVO.setNameSong((String) list.get().get(i)[4]);
            Security.decipherFromDB(songsVO);
            Security.encodeToClient(songsVO);
            listVo.add(songsVO);
        }
        return listVo;
    }


    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: משנה את התמונה של הפלייליסט במסד הנתונים.
    מחזירה: כלום.
    */
    public void changeImagePlayList(Long playListId){
            Optional<PlayListEntity> playListEntity;
            playListEntity=playListRepository.findById(playListId);
            if(!playListEntity.isPresent()){
                return;
            }
            Optional<List<Object>> albumsEntity;
            albumsEntity=playListRepository.getAlbumsFromPlayList(playListId);
            if(!albumsEntity.isPresent()){
                return;
            }
            PlayListVO playListVOToUpdate=new PlayListVO();
            if(albumsEntity.get().size()<4){
                playListVOToUpdate.setId(playListEntity.get().getId());
                playListVOToUpdate.setPlayListName(playListEntity.get().getPlayListName());
                playListVOToUpdate.setUserId(playListEntity.get().getUserId());
                playListVOToUpdate.setPublic1(playListEntity.get().isPublic1());
                playListVOToUpdate.setDate(playListEntity.get().getDate());
                byte[] image=(byte[]) albumsEntity.get().get(0);
                playListVOToUpdate.setImage(image);
                update(playListVOToUpdate);
                return ;
            }
            playListVOToUpdate.setId(playListEntity.get().getId());
            playListVOToUpdate.setPlayListName(playListEntity.get().getPlayListName());
            playListVOToUpdate.setUserId(playListEntity.get().getUserId());
            playListVOToUpdate.setPublic1(playListEntity.get().isPublic1());
            playListVOToUpdate.setDate(playListEntity.get().getDate());
            byte[]image=combineImages((byte[]) albumsEntity.get().get(0),(byte[]) albumsEntity.get().get(1),(byte[]) albumsEntity.get().get(2),(byte[]) albumsEntity.get().get(3));
            playListVOToUpdate.setImage(image);
            update(playListVOToUpdate);
    }


    /*
    מקבלת: 4 מערכים של בייתים המייצגים תמונות.
    מבצעת: מחברת את כולם לתמונה אחת.
    מחזירה: מחזירה את התמונה המחוברת.
    */
    private byte[] combineImages(byte[] image1, byte[] image2, byte[] image3, byte[] image4) {
        BufferedImage img1,img2,img3,img4;
        try{
            img1 = ImageIO.read(new ByteArrayInputStream(image1));
            img2 = ImageIO.read(new ByteArrayInputStream(image2));
            img3 = ImageIO.read(new ByteArrayInputStream(image3));
            img4 = ImageIO.read(new ByteArrayInputStream(image4));
        }catch (Exception e){
            return null;
        }

        int width = img1.getWidth() + img2.getWidth();
        int height = img1.getHeight() + img2.getHeight();

        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = combinedImage.createGraphics();

        g2d.drawImage(img1, 0, 0, null);
        g2d.drawImage(img2, img1.getWidth(), 0, null);
        g2d.drawImage(img3, 0, img1.getHeight(), null);
        g2d.drawImage(img4, img1.getWidth(), img1.getHeight(), null);

        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte;
        try {
            ImageIO.write(combinedImage, "jpeg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        }catch (Exception e){
            return null;
        }

        return imageInByte;
    }
    /*public List<SongsVO> getPlayListInfoById2(PlayListVO playListVO){
        Optional<List<SongsEntity>> list=null;
        Optional<List<Object[]>> listObg;
        listObg=playListRepository.getPlayListInfoByPlayListId2(playListVO.getId());
        for (int i = 0; i < listObg.get().size(); i++) {
            SongsEntity songsEntity=new SongsEntity();
            songsEntity.setId((Long) listObg.get().get(i)[0]);
            songsEntity.setUserId((Long) listObg.get().get(i)[1]);
            songsEntity.setZaner((String) listObg.get().get(i)[2]);
            songsEntity.setDate((Date) listObg.get().get(i)[3]);
            songsEntity.setNameSong((String) listObg.get().get(i)[4]);
            songsEntity.setSongPath((String) listObg.get().get(i)[5]);
            list.get().add(songsEntity);

        }
        if(!list.isPresent()){
            return null;
        }
        List<SongsVO> listVo = new ArrayList<>();
        for (int i = 0; i < list.get().size(); i++) {
            SongsEntity songsEntity=new SongsEntity();
            SongsVO songsVO=new SongsVO();
            BeanUtils.copyProperties(list.get().get(i),songsEntity);
            Security.decipherFromDB(songsEntity);
            BeanUtils.copyProperties(songsEntity,songsVO);
            Security.encodeToClient(songsVO);
            listVo.add(songsVO);
        }
        return listVo;
    }
    */
}
