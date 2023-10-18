package com.example.music_fly_project.server;

import javax.sound.sampled.AudioFormat;
import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.enums.ErrorsEnumForPlayList;
import com.example.music_fly_project.logic.PlayListLogic;
import com.example.music_fly_project.logic.Security;
import com.example.music_fly_project.logic.SongsLogic;
import com.example.music_fly_project.repository.PlayListRepository;
import com.example.music_fly_project.vo.PlayListVO;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayListServer {
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private ConnectionSongPlayListServer conSongPlayListServer;
    public PlayListVO save(PlayListVO playListVO){
        PlayListEntity bean= new PlayListEntity();
        playListVO.setDate(new Date());
        BeanUtils.copyProperties(playListVO,bean);
        bean=playListRepository.save(bean);
        BeanUtils.copyProperties(bean,playListVO);
        playListVO.setE(ErrorsEnumForPlayList.GOOD);
        return playListVO;
    }
    public ErrorsEnumForPlayList delete(long id){
        Optional<PlayListEntity>playList;
        playList=geyById(id);
        if(!playList.isPresent()){
            return ErrorsEnumForPlayList.PLAY_LIST_NOT_FOUND;
        }
        conSongPlayListServer.delAllConByPlayListId(id);
        playListRepository.deleteById(id);
        return ErrorsEnumForPlayList.GOOD;
    }
    public ErrorsEnumForPlayList update(PlayListVO playListVO){
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
    private  Optional<PlayListEntity> geyById(long id){
        Optional<PlayListEntity> user=playListRepository.findById(id);
        return user;
    }
    public List<PlayListVO> getPlayListByName(PlayListVO playListVO) {
        Optional<List<PlayListVO>>list;
        list=playListRepository.getPlayListByName(playListVO.getPlayListName(),playListVO.getUserId());
        if(!list.isPresent()){
            return null;
        }
        return list.get();

    }
    public List<PlayListVO> getPlayListByUserId(PlayListVO playListVO){
        Optional<List<PlayListEntity>>list;
        list=playListRepository.getPlayListUserId(playListVO.getUserId());
        if(!list.isPresent()){
            return null;
        }
        PlayListLogic playListLogic=new PlayListLogic();
        List<PlayListVO> listVOS;
        listVOS=playListLogic.copyList(list.get());
        return listVOS;
    }
    public List<SongsVO> getPlayListById(PlayListVO playListVO){
        Optional<List<SongsEntity>> list;
        list=playListRepository.getPlayListByPlayListId(playListVO.getId());
        if(!list.isPresent()){
            return null;
        }
        for (int i = 0; i <list.get().size() ; i++) {
            Security.decipherFromDB(list.get().get(i));
        }
        SongsLogic songsLogic=new SongsLogic();
        List<SongsVO>voList;
        voList=songsLogic.copyListEntityToVO(list.get());
        for (int i = 0; i < voList.size(); i++) {
            Security.encodeToClient(voList.get(i));
        }
        return voList;
    }
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
            if(albumsEntity.get().size()<4){
                PlayListVO playListVOToUpdate=new PlayListVO();
                playListVOToUpdate.setId(playListEntity.get().getId());
                playListVOToUpdate.setPlayListName(playListEntity.get().getPlayListName());
                playListVOToUpdate.setUserId(playListEntity.get().getUserId());
                playListVOToUpdate.setPublic1(playListEntity.get().isPublic1());
                playListVOToUpdate.setDate(playListEntity.get().getDate());
                playListVOToUpdate.setLengthPlayList(playListEntity.get().getLengthPlayList());
                byte[] image=(byte[]) albumsEntity.get().get(0);
                playListVOToUpdate.setImage(image);
                update(playListVOToUpdate);
                return ;
            }
            PlayListVO playListVOToUpdate=new PlayListVO();
            playListVOToUpdate.setId(playListEntity.get().getId());
            playListVOToUpdate.setPlayListName(playListEntity.get().getPlayListName());
            playListVOToUpdate.setUserId(playListEntity.get().getUserId());
            playListVOToUpdate.setPublic1(playListEntity.get().isPublic1());
            playListVOToUpdate.setDate(playListEntity.get().getDate());
            playListVOToUpdate.setLengthPlayList(playListEntity.get().getLengthPlayList());
            byte[]image=combineImages((byte[]) albumsEntity.get().get(0),(byte[]) albumsEntity.get().get(1),(byte[]) albumsEntity.get().get(2),(byte[]) albumsEntity.get().get(4));
            playListVOToUpdate.setImage(image);
            update(playListVOToUpdate);
    }
    private int getAudioLengthInSeconds(byte[] audioData) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioData));
            AudioFormat format = audioInputStream.getFormat();
            int audioLengthInSeconds = (int) ((audioData.length / format.getFrameSize()) / format.getSampleRate());
            return audioLengthInSeconds;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }
    public void updateLengthPlayList(Long playListId,Long newSongId){
        Optional<PlayListEntity> playListEntity;
        playListEntity=geyById(playListId);
        if(!playListEntity.isPresent()){
            return;
        }
        Optional<SongsEntity> songsEntity;
        songsEntity=playListRepository.getSongById(newSongId);
        if(!songsEntity.isPresent()){
            return;
        }
        SongsEntity songsEntity1=new SongsEntity();
        BeanUtils.copyProperties(songsEntity.get(),songsEntity1);
        Security.decipherFromDB(songsEntity1);
        int sec=getAudioLengthInSeconds(songsEntity1.getTheSong());
        PlayListVO playListVO=new PlayListVO();
        BeanUtils.copyProperties(playListEntity.get(),playListVO);
        playListVO.setLengthPlayList(sec);
        update(playListVO);
    }
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
}
