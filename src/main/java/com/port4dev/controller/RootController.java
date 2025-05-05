package com.port4dev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String home() {
        return "✅ 포트폴리오 서버가 정상 작동 중입니다.";
    }
}   