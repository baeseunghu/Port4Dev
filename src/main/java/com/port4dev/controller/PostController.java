package com.port4dev.controller;

import com.port4dev.dto.PostRequest;
import com.port4dev.dto.PostResponse;
import com.port4dev.security.CustomUserDetails;
import com.port4dev.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // ✅ 게시글 등록
    @PostMapping
    public ResponseEntity<?> createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestBody PostRequest request) {
        String userEmail = userDetails.getUsername();
        postService.createPost(request, userEmail);
        return ResponseEntity.ok("✅ 게시글이 등록되었습니다.");
    }

    // ✅ 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // ✅ 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // ✅ 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestBody PostRequest request) {
        postService.updatePost(id, userDetails.getUsername(), request);
        return ResponseEntity.ok("✅ 수정 완료");
    }

    // ✅ 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(id, userDetails.getUsername());
        return ResponseEntity.ok("✅ 삭제 완료");
    }

    // ✅ 로그인한 사용자의 게시글 목록
    @GetMapping("/my")
    public ResponseEntity<List<PostResponse>> getMyPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getUsername();
        return ResponseEntity.ok(postService.getPostsByUser(email));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam("keyword") String keyword) {
        List<PostResponse> results = postService.searchPosts(keyword);
        return ResponseEntity.ok(results);
    }
}