package com.example.music_fly_project.server;
import com.example.music_fly_project.entity.ConnectionSongAlbumEntity;
import com.example.music_fly_project.enums.ErrosEnumForConnectionSongAlbum;
import com.example.music_fly_project.logic.ConnectionSongForAlbumLogic;
import com.example.music_fly_project.repository.ConnectionSongAlbumRepository;
import com.example.music_fly_project.vo.ConnectionSongAlbumsVO;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ConnectionSongAlbumServer {

    @Autowired
    private ConnectionSongAlbumRepository connectionSongAlbumRepository;
    public ErrosEnumForConnectionSongAlbum save(ConnectionSongAlbumsVO conVO){
        ConnectionSongAlbumEntity bean =new ConnectionSongAlbumEntity();
        BeanUtils.copyProperties(conVO,bean);
        try {
            connectionSongAlbumRepository.save(bean);
        }catch (Exception e){
            System.out.println(e);
            return ErrosEnumForConnectionSongAlbum.NOT_SAVED_SUCCESSFULLY;
        }
        return ErrosEnumForConnectionSongAlbum.GOOD;
    }
    public ErrosEnumForConnectionSongAlbum delete(long id){
        Optional<ConnectionSongAlbumEntity> con;
        con=getById(id);
        if(!con.isPresent()){
            return ErrosEnumForConnectionSongAlbum.CONNECTION_NOT_FOUND;
        }
        connectionSongAlbumRepository.deleteById(id);
        return ErrosEnumForConnectionSongAlbum.GOOD;
    }
    public ErrosEnumForConnectionSongAlbum update(ConnectionSongAlbumsVO conVO){
        Optional<ConnectionSongAlbumEntity> con;
        con=getById(conVO.getId());
        if(!con.isPresent()){
            return ErrosEnumForConnectionSongAlbum.CONNECTION_NOT_FOUND;
        }
//        if(albumsVO.equals(albums.get())){
//            return ErrorsEnumForAlbums.TheSameAlbum;
//        }
        ConnectionSongAlbumEntity bean=new ConnectionSongAlbumEntity();
        BeanUtils.copyProperties(conVO,bean);
        connectionSongAlbumRepository.save(bean);
        return ErrosEnumForConnectionSongAlbum.GOOD;
    }
    private Optional<ConnectionSongAlbumEntity> getById(long id){
        return connectionSongAlbumRepository.findById(id);
    }
    public List<ConnectionSongAlbumsVO> getConnectionByAlbum(long id){
        Optional<List<ConnectionSongAlbumEntity>>list;
        list=connectionSongAlbumRepository.getSongForAlbum(id);
        if(!list.isPresent()){
            return null;
        }
        List<ConnectionSongAlbumsVO>listAlbum=new ArrayList<>();
        ConnectionSongForAlbumLogic.copyProperty(list.get(),listAlbum);
        return listAlbum;
    }
    public List<ConnectionSongAlbumsVO> getConnectionBySongId(long id){
        Optional<List<ConnectionSongAlbumEntity>> list;
        List<ConnectionSongAlbumsVO> listVo=new ArrayList<>();
        list=connectionSongAlbumRepository.getConnectionBySongId(id);
        if(list.isPresent()){
            return null;
        }
        ConnectionSongForAlbumLogic.copyProperty(list.get(),listVo);
        return listVo;
    }

}
