package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.PlayListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListRepository extends JpaRepository<PlayListEntity,Long> {
}
