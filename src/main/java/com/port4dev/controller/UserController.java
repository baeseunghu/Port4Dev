package com.port4dev.controller;

import com.port4dev.dto.RegisterRequest;
import com.port4dev.dto.RegisterResponse;
import com.port4dev.entity.User;
import com.port4dev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            System.out.println("👉 이메일: " + request.getEmail());
            System.out.println("👉 비밀번호: " + request.getPassword());
            System.out.println("👉 이름: " + request.getName()); // ✅ name 로그

            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setName(request.getName()); // ✅ name 설정
            user.setRole("ROLE_USER");

            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(new RegisterResponse(savedUser.getEmail(), "회원가입 완료"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RegisterResponse(null, "❌ 오류: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new RegisterResponse(null, "❌ 서버 오류: " + e.getMessage()));
        }
    }
}