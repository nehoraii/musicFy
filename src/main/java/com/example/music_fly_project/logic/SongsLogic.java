package com.example.music_fly_project.logic;

import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForSongs;
import com.example.music_fly_project.vo.SongsVO;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class SongsLogic {
    public static int sizeCheckCopyright=5;
    public static ErrorsEnumForSongs checkSong(byte[] song1, byte[] song2){
        //בדיקת זכויות יוצאים
        int sampleRate1=getSampleRate(song1);
        int sampleRate2=getSampleRate(song2);
        int sampleRate=Math.min(sampleRate2,sampleRate1);
        boolean answer=checkCopyright(song1,song2,sampleRate,sizeCheckCopyright);
        if(answer){
            return ErrorsEnumForSongs.COPYRIGHT;
        }
        return ErrorsEnumForSongs.GOOD;
    }
    private static boolean checkCopyright(byte[] song1,byte[] song2,int sampleRate,int seconds){
        int samplesToCompare = sampleRate * seconds;
        int startIndex = song2.length - samplesToCompare;

        // השוואת חמש השניות הראשונות בשיר הראשון לחמש השניות האחרונות בשיר השני
        for (int i = 0; i < samplesToCompare; i++) {
            if (song1[i] == song2[startIndex + i]) {
                return true;
            }
        }
        return false;
    }
    private static int getSampleRate(byte[] song){
        try{

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(song);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(audioInputStream);
            int sampleRate = (int) fileFormat.getProperty("duration"); // קצב הדוגמה
            return sampleRate;
        }catch (Exception e){
            System.out.println(e);
        }
        return -1;
    }
    public static List<SongsVO> copyListEntityToVO(List<SongsEntity> listEntity){
        List<SongsVO> list=new ArrayList<>();
        SongsVO songsVO;
        for (int i = 0; i < listEntity.size(); i++) {
            songsVO=new SongsVO();
            songsVO.setNameSong(listEntity.get(i).getNameSong());
            songsVO.setZaner(listEntity.get(i).getZaner());
            songsVO.setId(listEntity.get(i).getId());
            songsVO.setTheSong(null);
            songsVO.setDate(listEntity.get(i).getDate());
            songsVO.setUserId(listEntity.get(i).getUserId());
            list.add(songsVO);
        }
        return list;
    }
    public static void copyProperty(SongsVO from, SongsEntity to){
        to.setTheSong(from.getTheSong());
        to.setId(from.getId());
        to.setDate(from.getDate());
        to.setZaner(from.getZaner());
        to.setUserId(from.getUserId());
        to.setNameSong(from.getNameSong());
    }
    public static void copyProperty(SongsEntity from, SongsVO to){
        to.setTheSong(from.getTheSong());
        to.setDate(from.getDate());
        to.setZaner(from.getZaner());
        to.setUserId(from.getUserId());
        to.setNameSong(from.getNameSong());
        to.setId(from.getId());
    }
}
