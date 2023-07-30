package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.ConnectionSongPlayListEntity;
import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.vo.ConnectionSongPlayListVO;
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


}
