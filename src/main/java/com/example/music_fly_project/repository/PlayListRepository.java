package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.entity.SongsEntity;
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
public interface PlayListRepository extends JpaRepository<PlayListEntity,Long> {
    @Query(value = "SELECT * FROM play_list WHERE play_list_name LIKE %:name%  AND public=TRUE  AND id > :lastIdPlayList LIMIT :lim",nativeQuery = true)
    Optional<List<PlayListEntity>> getPlayListByName(@Param("name") String Name,@Param("lastIdPlayList")Long playListId,@Param("lim") int limit);
    @Query("SELECT e FROM PlayListEntity e WHERE e.userId=:userId")
    Optional<List<PlayListEntity>> getPlayListsByUserId(@Param("userId")Long userId);
    @Query("SELECT s.id FROM SongsEntity s JOIN ConnectionSongPlayListEntity e ON s.id=e.songId WHERE e.playListId=:playListId")
    Optional<List<Object>>getSongsIdByPlayListId(@Param("playListId")Long playListId);
    @Query("SELECT s FROM SongsEntity s JOIN ConnectionSongPlayListEntity e ON s.id=e.songId WHERE e.playListId=:playListId")
    Optional<List<SongsEntity>>getPlayListInfoByPlayListId(@Param("playListId")Long playListId);
    /*@Query(value = "SELECT songs.id,user_id,zaner,date,name_song,song_path FROM songs JOIN connection_song_play_list ON songs.id=connection_song_play_list.song_id WHERE connection_song_play_list.play_list_id=?1",nativeQuery = true)
    Optional<List<Object[]>>getPlayListInfoByPlayListId2(long playListId);*/
    @Query(value = "SELECT DISTINCT albums.image_album FROM albums JOIN connection_song_album ON albums.id = connection_song_album.album_id JOIN songs ON songs.id = connection_song_album.song_id JOIN connection_song_play_list ON connection_song_play_list.song_id = songs.id JOIN play_list ON play_list.id = connection_song_play_list.play_list_id WHERE play_list.id =:playListId LIMIT 4",nativeQuery = true)
    Optional<List<Object>> getAlbumsFromPlayList(@Param("playListId")Long playListId);
    /*@Query("SELECT s.theSong FROM SongsEntity s WHERE s.id=:songId")
    Optional<Object[]> getSongById(@Param("songId")Long songId);

     */
    @Modifying
    @Transactional
    @Query(value = "DElETE FROM connection_song_play_list WHERE play_list_id=:playListId",nativeQuery = true)
    void delAllConByPlayListId(@Param("playListId")Long playListId);
    @Query("SELECT COUNT(c) FROM ConnectionSongPlayListEntity c WHERE c.playListId=:playListId")
    int getLengthPlayList(@Param("playListId") Long playListId);

}
