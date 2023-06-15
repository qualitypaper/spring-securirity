package com.qualitypaper.co_tourism.controller;

import com.qualitypaper.co_tourism.controller.dto.CommentRequest;
import com.qualitypaper.co_tourism.controller.dto.CommentResponse;
import com.qualitypaper.co_tourism.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    public CommentResponse createComment(CommentRequest commentRequest){
        return commentService.createComment(commentRequest);
    }
}
