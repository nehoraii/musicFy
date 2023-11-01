package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface AlbumsRepository extends JpaRepository<AlbumsEntity,Long> {
    @Query("SELECT s FROM SongsEntity s JOIN ConnectionSongAlbumEntity e ON s.id=e.songId WHERE e.albumId=:albumId")
    Optional<List<SongsEntity>> getAlbumsSongByAlbumId(@Param("albumId")long albumId);
    @Query("SELECT a FROM AlbumsEntity a WHERE a.userId=:userId")
    Optional<List<AlbumsEntity>> getAlbumOfUser(@Param("userId") Long userId);
    @Query("SELECT s.id FROM SongsEntity s JOIN ConnectionSongAlbumEntity e ON s.id=e.songId WHERE e.albumId=:albumId")
    Optional<List<Object>> getAlbumsSongIdByAlbumId(@Param("albumId")long albumId);
    @Query(value = "SELECT * FROM albums WHERE name_album LIKE %:name% AND id > :lastIdAlbum LIMIT :lim",nativeQuery = true)
    Optional<List<AlbumsEntity>> getAlbumsByName(@Param("name") String Name,@Param("lastIdAlbum")Long albumId,@Param("lim") int limit);
}
