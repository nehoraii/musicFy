package com.example.music_fly_project.repository;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.vo.SongsVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface SongsRepository extends JpaRepository<SongsEntity,Long> {
    @Query("SELECT e FROM SongsEntity e WHERE e.userId=?1")
    Optional<List<SongsEntity>> getByUserId(long id);
    @Query(value = "SELECT * FROM songs WHERE name_song LIKE %:name%",nativeQuery = true)
    Optional<List<SongsEntity>> getSongByName(@Param("name") String Name);
    @Query("SELECT e FROM SongsEntity e WHERE e.userId IN :ListSongId")
    Optional<List<SongsEntity>> getSongByListId(@Param("ListSongId")List<Long> listId);
    @Query("SELECT e FROM SongsEntity e WHERE e.userId=:userId")
    Optional<List<SongsEntity>> getSongsByUserId(@Param("userId") Long userId);
}
