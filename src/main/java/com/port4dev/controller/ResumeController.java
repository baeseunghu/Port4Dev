package com.port4dev.controller;

import com.port4dev.dto.ResumeDto;
import com.port4dev.entity.Resume;
import com.port4dev.entity.User;
import com.port4dev.repository.ResumeRepository;
import com.port4dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    // ✅ 이력서 저장
    @PostMapping
    public ResponseEntity<?> saveResume(@RequestBody ResumeDto dto, Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        Resume resume = new Resume();
        resume.setName(dto.name());
        resume.setEmail(dto.email());
        resume.setExperience(dto.experience());
        resume.setSkills(dto.skills());
        resume.setUser(user);

        resumeRepository.save(resume);

        return ResponseEntity.ok("이력서 저장 완료");
    }

    // ✅ 이력서 조회 (마이페이지에서 사용)
    @GetMapping
    public ResponseEntity<?> getResume(Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        Resume resume = resumeRepository.findByUserId(user.getId());
        if (resume == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resume);
    }
}