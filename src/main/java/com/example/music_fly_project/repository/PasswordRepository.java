package com.example.music_fly_project.repository;
import com.example.music_fly_project.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<PasswordEntity,Long> {
}
