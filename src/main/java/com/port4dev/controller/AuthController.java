package com.port4dev.controller;

import com.port4dev.dto.LoginRequest;
import com.port4dev.dto.LoginResponse;
import com.port4dev.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        String email = request.getEmail();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String token = jwtTokenProvider.createToken(email, role);

        return ResponseEntity.ok(new LoginResponse(email, role, token));
    }
}