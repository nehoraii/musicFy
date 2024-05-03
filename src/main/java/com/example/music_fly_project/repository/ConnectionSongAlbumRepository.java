package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.ConnectionSongAlbumEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//ממשק שאחראי על החיבור למסד הנתונים ומביא את הנתונים הרלוונטים לפי השאילתה המתאימה
public interface ConnectionSongAlbumRepository extends  JpaRepository<ConnectionSongAlbumEntity,Long> {

    /*
    מקבלת: המזהה הייחודי של השיר
    מבצעת: מוחקת את כל הקשרים בין השיר לאלבום.
    מחזירה: כלום.
    */
    @Modifying
    @Transactional
    @Query(value = "DElETE FROM connection_song_album WHERE song_id=:songId",nativeQuery = true)
    void delConBySongId(@Param("songId")Long songId);
}
