package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.ConnectionSongPlayListEntity;
import com.example.music_fly_project.entity.PlayListEntity;
import com.example.music_fly_project.vo.ConnectionSongPlayListVO;
import com.example.music_fly_project.vo.PlayListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionSongPlayListRepository extends JpaRepository<ConnectionSongPlayListEntity,Long> {
    @Query("SELECT E FROM ConnectionSongPlayListEntity  E WHERE E.playListId=:id")
    Optional<List<ConnectionSongPlayListVO>> getConByPlayListId(@Param("id")long id);
}
