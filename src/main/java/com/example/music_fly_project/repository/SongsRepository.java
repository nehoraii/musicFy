package com.example.music_fly_project.repository;
import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SongsRepository extends JpaRepository<SongsEntity,Long> {

}
