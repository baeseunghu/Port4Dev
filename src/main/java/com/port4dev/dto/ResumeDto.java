package com.port4dev.dto;

public record ResumeDto(
    Long userId,
    String name,
    String email,
    String experience,
    String skills
) {}