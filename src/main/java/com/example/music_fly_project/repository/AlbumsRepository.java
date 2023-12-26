package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM connection_song_play_list WHERE song_id IN (SELECT id FROM songs WHERE id IN (SELECT song_id FROM connection_song_album WHERE album_id = :albumId));",nativeQuery = true)
    void DelAllConPlayList(@Param("albumId")Long albumId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM connection_song_album WHERE song_id IN (SELECT id FROM songs WHERE id IN (SELECT song_id FROM connection_song_album WHERE album_id = :albumId))",nativeQuery = true)
    void DelAllConAlbumSong(@Param("albumId")Long albumId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM songs WHERE id IN (SELECT song_id FROM connection_song_album WHERE album_id = :albumId);",nativeQuery = true)
    void DelAllSong(@Param("albumId")Long albumId);
    /*@Query("SELECT s.theSong FROM SongsEntity s WHERE s.id=:songId")
    Optional<Object> getSongById(@Param("songId")Long songId);
     */
    @Query("SELECT COUNT(c) FROM ConnectionSongAlbumEntity c WHERE c.albumId=:albumId")
    int getLengthAlbum(@Param("albumId") Long albumId);
    @Query("SELECT s.songPath,s.userId FROM SongsEntity s JOIN ConnectionSongAlbumEntity c ON c.songId=s.id WHERE c.albumId=:albumId")
    Optional<List<Object[]>> getSongsPath(@Param("albumId") long albumId);

}
