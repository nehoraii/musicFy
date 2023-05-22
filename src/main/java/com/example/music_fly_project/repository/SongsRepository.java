package com.example.music_fly_project.repository;
import com.example.music_fly_project.entity.SongsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongsRepository extends JpaRepository<SongsEntity,Long> {

}
