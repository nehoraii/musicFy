package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.vo.PlayListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayListRepository extends JpaRepository<PlayListEntity,Long> {
    @Query("SELECT e FROM PlayListEntity e WHERE e.playListName LIKE :name  AND e.public1=TRUE OR e.userId=:userId")
    Optional<List<PlayListVO>> getPlayListByName(@Param("name")String namePlayList,@Param("userId") long userId);
    @Query("SELECT e FROM PlayListEntity e WHERE e.userId=:userId")
    Optional<List<PlayListEntity>> getPlayListUserId(@Param("userId")long userId);
    @Query("SELECT s FROM SongsEntity s JOIN ConnectionSongPlayListEntity e ON s.id=e.songId WHERE e.playListId=:playListId")
    Optional<List<SongsEntity>>getPlayListByPlayListId(@Param("playListId")long playListId);
    @Query(value = "SELECT DISTINCT albums.image_album FROM albums JOIN connection_song_album ON albums.id = connection_song_album.album_id JOIN songs ON songs.id = connection_song_album.song_id JOIN connection_song_play_list ON connection_song_play_list.song_id = songs.id JOIN play_list ON play_list.id = connection_song_play_list.play_list_id WHERE play_list.id =:playListId LIMIT 4",nativeQuery = true)
    Optional<List<Object>> getAlbumsFromPlayList(@Param("playListId")Long playListId);
    @Query("SELECT s FROM SongsEntity s WHERE s.id=:songId")
    Optional<SongsEntity> getSongById(@Param("songId")Long songId);

}
