package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.ConnectionSongAlbumEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConnectionSongAlbumRepository extends  JpaRepository<ConnectionSongAlbumEntity,Long> {
    @Query("SELECT e FROM ConnectionSongAlbumEntity e where e.albumId=:id")
    Optional<List<ConnectionSongAlbumEntity>> getSongForAlbum(@Param("id")long id);
    @Query("SELECT e FROM ConnectionSongAlbumEntity  e WHERE e.songId=:songId")
    Optional<List<ConnectionSongAlbumEntity>> getConnectionBySongId(@Param("songId") long songId);
    @Modifying
    @Transactional
    @Query(value = "DElETE FROM connection_song_album WHERE song_id=:songId",nativeQuery = true)
    void DelConBySongId(@Param("songId")Long songId);
}
