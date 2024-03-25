package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.ConnectionSongPlayListEntity;
import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.vo.ConnectionSongPlayListVO;
import com.example.music_fly_project.vo.PlayListVO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionSongPlayListRepository extends JpaRepository<ConnectionSongPlayListEntity,Long> {
    @Query(value = "SELECT * FROM connection_song_play_list WHERE song_id=:songId AND play_list_id=:playListId LIMIT 1",nativeQuery = true)
    Optional<ConnectionSongPlayListEntity> getConBySongIdAndPlayListId(@Param("songId")Long id,@Param("playListId")Long playListId);

    @Modifying
    @Transactional
    @Query(value = "DElETE FROM connection_song_play_list WHERE song_id=:songId",nativeQuery = true)
    void delConBySongId(@Param("songId")Long songId);

}
