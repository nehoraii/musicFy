package com.example.music_fly_project.repository;
import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SongsRepository extends JpaRepository<SongsEntity,Long> {
    @Query(value = "SELECT * FROM songs WHERE name_song LIKE %:name% AND id > :lastIdSong LIMIT :lim",nativeQuery = true)
    Optional<List<SongsEntity>> getSongByName(@Param("name") String Name,@Param("lastIdSong")Long songId,@Param("lim") int limit);
    @Query("SELECT e from AlbumsEntity e JOIN ConnectionSongAlbumEntity c ON c.albumId=e.id WHERE c.songId=:songId")
    Optional<AlbumsEntity> getAlbumBySongId(@Param("songId") Long songId);


}
