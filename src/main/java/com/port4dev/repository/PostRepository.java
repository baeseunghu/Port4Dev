// ✅ 1. PostRepository.java에 검색 메서드 추가

package com.port4dev.repository;

import com.port4dev.entity.Post;
import com.port4dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByIdDesc();
    List<Post> findByUserOrderByIdDesc(User user);

    // ✅ 제목 또는 내용에 키워드가 포함된 게시글 검색
    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
}
