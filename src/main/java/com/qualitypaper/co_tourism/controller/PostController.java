package com.qualitypaper.co_tourism.controller;

import com.qualitypaper.co_tourism.controller.dto.PostRequest;
import com.qualitypaper.co_tourism.controller.dto.PostResponse;
import com.qualitypaper.co_tourism.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post/")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.createPost(postRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.updatePost(id, postRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
