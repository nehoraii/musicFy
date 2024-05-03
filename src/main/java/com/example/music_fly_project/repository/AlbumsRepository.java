package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.SongsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//ממשק ששאחראי להביא את המידע הרלוונטי מהמסד נתונים על פי השאילתה המתאימה
public interface AlbumsRepository extends JpaRepository<AlbumsEntity,Long> {


    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: מביאה את כל השירים שנמצאים באלבום.
    מחזירה: רשימה של שירים הנמצאים באלבום.
    */
    @Query("SELECT s FROM SongsEntity s JOIN ConnectionSongAlbumEntity e ON s.id=e.songId WHERE e.albumId=:albumId")
    Optional<List<SongsEntity>> getAlbumsSongByAlbumId(@Param("albumId")Long albumId);



    /*
    מקבלת: המזהה הייחודי של המשתמש.
    מבצעת: מביאה את כל האלבומים שלו.
    מחזירה: רשימה של האלבומים שלו.
    */
    @Query("SELECT a FROM AlbumsEntity a WHERE a.userId=:userId")
    Optional<List<AlbumsEntity>> getAlbumsOfUser(@Param("userId") Long userId);


    /*
    מקבלת: המזהה הייחודי של המשתמש.
    מבצעת: מביאה את האלבום הראשון.
    מחזירה: אובייקט המייצג אלבום.
    */
    @Query(value = "SELECT * FROM albums WHERE user_id=:userId LIMIT 1",nativeQuery = true)
    Optional<AlbumsEntity> getAlbumByUserIdLIMIT(@Param("userId")Long userId);



    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: המזהים הייחודים של השירים הנמצאים באלבום.
    מחזירה: רשימה של אובייקטים שבהם יהיה את המזהים הייחודים של השירים הנמאים באלבום.
    */
    @Query("SELECT s.id FROM SongsEntity s JOIN ConnectionSongAlbumEntity e ON s.id=e.songId WHERE e.albumId=:albumId")
    Optional<List<Object>> getAlbumsSongIdByAlbumId(@Param("albumId")Long albumId);



    /*
    מקבלת: שם וגבול.
    מבצעת: מביאה את כל האלבומים ששמם שווה /דומה לשם שנשלח. בגבול שנשלח.
    מחזירה:רשימה של האלבומים.
    */
    @Query(value = "SELECT * FROM albums WHERE name_album LIKE %:name% AND id > :lastIdAlbum LIMIT :lim",nativeQuery = true)
    Optional<List<AlbumsEntity>> getAlbumsByName(@Param("name") String Name,@Param("lastIdAlbum")Long albumId,@Param("lim") int limit);



    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: מוחקת את כל הקשרים בין השירים שהיו באלבום לפלייליסט.
    מחזירה: כלום.
    */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM connection_song_play_list WHERE song_id IN (SELECT id FROM songs WHERE id IN (SELECT song_id FROM connection_song_album WHERE album_id = :albumId));",nativeQuery = true)
    void delAllConPlayList(@Param("albumId")Long albumId);



    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: מוחקת את כל הקשרים בין השירים שהיו באלבום לאלבום.
    מחזירה: כלום.
    */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM connection_song_album WHERE song_id IN (SELECT id FROM songs WHERE id IN (SELECT song_id FROM connection_song_album WHERE album_id = :albumId))",nativeQuery = true)
    void delAllConAlbumSong(@Param("albumId")Long albumId);



    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: מוחקת את כל השירים.
    מחזירה: כלום.
    */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM songs WHERE id IN (SELECT song_id FROM connection_song_album WHERE album_id = :albumId);",nativeQuery = true)
    void delAllSong(@Param("albumId")Long albumId);
    /*@Query("SELECT s.theSong FROM SongsEntity s WHERE s.id=:songId")
    Optional<Object> getSongById(@Param("songId")Long songId);
     */



    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: סופרת כמה קשרים יש לאלבום הזה (כלומר כמה שיירם יש באלבום).
    מחזירה: מספר הקשרים.
    */
    @Query("SELECT COUNT(c) FROM ConnectionSongAlbumEntity c WHERE c.albumId=:albumId")
    int getLengthAlbum(@Param("albumId") Long albumId);


    /*
    מקבלת: המזהה הייחודי של האלבום.
    מבצעת: מביאה את הנתיב של כל השירים הנמצאים באלבום.
    מחזירה: רשימה של הנתיבים והמזהה הייחודי של המשתמש שהשיר הזה הוא שלו.
    */
    @Query("SELECT s.songPath,s.userId FROM SongsEntity s JOIN ConnectionSongAlbumEntity c ON c.songId=s.id WHERE c.albumId=:albumId")
    Optional<List<Object[]>> getSongsPath(@Param("albumId") Long albumId);

}
