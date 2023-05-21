package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.AlbumsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.music_fly_project.entity.AlbumsEntity;
import org.springframework.data.repository.query.Param;

public interface AlbumsRepository extends JpaRepository<AlbumsEntity,Long> {
}
