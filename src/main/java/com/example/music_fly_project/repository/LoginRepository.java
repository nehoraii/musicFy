package com.example.music_fly_project.repository;

import com.example.music_fly_project.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginEntity,Long> {
}
