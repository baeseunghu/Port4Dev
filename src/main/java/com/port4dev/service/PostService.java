package com.port4dev.service;

import com.port4dev.dto.PostRequest;
import com.port4dev.dto.PostResponse;
import com.port4dev.entity.Post;
import com.port4dev.entity.User;
import com.port4dev.repository.PostRepository;
import com.port4dev.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void createPost(PostRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("작성자 정보를 찾을 수 없습니다."));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .user(user)
                .build();

        postRepository.save(post);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByIdDesc().stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return PostResponse.from(post);
    }

    public void updatePost(Long postId, String userEmail, PostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("본인의 글만 수정할 수 있습니다.");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        postRepository.save(post);
    }

    public void deletePost(Long postId, String userEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("본인의 글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    public List<PostResponse> getPostsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        return postRepository.findByUserOrderByIdDesc(user)
                .stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    public List<PostResponse> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
}