package com.qualitypaper.co_tourism.service;

import com.qualitypaper.co_tourism.controller.dto.PostRequest;
import com.qualitypaper.co_tourism.controller.dto.PostResponse;
import com.qualitypaper.co_tourism.mappers.post.PostMapperImplementation;
import com.qualitypaper.co_tourism.model.Post;
import com.qualitypaper.co_tourism.repository.PostRepository;
import com.qualitypaper.co_tourism.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapperImplementation postMapperImplementation;
    private final JwtService jwtService;

    public PostResponse createPost(PostRequest postRequest) {
        Post post = postMapperImplementation.mapToPost(postRequest);
        extractAndSetPost(post, postRequest.token());
        var response = saveAndMapPostToPostResponse(post);
        return response;
    }

    public PostResponse updatePost(Long id, PostRequest postRequest) {
        var post = findAndSetPost(id, postRequest);
        var response = saveAndMapPostToPostResponse(post);
        return response;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    private Post findAndSetPost(Long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).get();
        post.setContent(postRequest.content());
        post.setTitle(postRequest.title());
        post.setImageUrl(postRequest.imageUrl());
        return post;
    }

    private PostResponse saveAndMapPostToPostResponse(Post post) {
        Post save = postRepository.save(post);
        PostResponse response = postMapperImplementation.mapToPostResponse(save);
        return response;
    }

    private void extractAndSetPost(Post post, String token) {
        Long user_id = jwtService.extractClaim(token, "id");
        post.setUser(userRepository.findById(user_id).get());
        post.setComments(Collections.emptyList());
    }
}
