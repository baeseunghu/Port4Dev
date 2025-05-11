package com.port4dev.dto;

import com.port4dev.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String category;    // ✅ 추가
    private String authorName;

    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getCategory(),
            post.getUser().getName()
        );
    }
}
