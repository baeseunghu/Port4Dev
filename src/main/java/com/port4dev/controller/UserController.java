package com.port4dev.controller;

import com.port4dev.dto.*;
import com.port4dev.entity.User;
import com.port4dev.jwt.JwtTokenProvider;
import com.port4dev.security.CustomUserDetails;
import com.port4dev.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // ✅ 회원가입
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setRole("ROLE_USER");

        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(new RegisterResponse(savedUser.getEmail(), "회원가입 완료"));
    }

    // ✅ 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(new LoginResponse(user.getEmail(), user.getRole(), token));
    }

    // ✅ 로그인한 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        return ResponseEntity.ok(new UserInfoResponse(user.getEmail(), user.getName(), user.getRole()));
    }

    // ✅ 사용자 정보 수정
    @PutMapping("/me")
    public ResponseEntity<?> updateMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestBody UpdateUserRequest request) {
        try {
            userService.updateUser(userDetails.getUsername(), request.getName());
            return ResponseEntity.ok().body("수정 완료");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ 수정 중 오류 발생");
        }
    }
}