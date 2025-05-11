package com.port4dev.repository;

import com.port4dev.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Resume findByUserId(Long userId);
}