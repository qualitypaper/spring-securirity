package com.qualitypaper.co_tourism.service;

import com.qualitypaper.co_tourism.controller.dto.CommentRequest;
import com.qualitypaper.co_tourism.controller.dto.CommentResponse;
import com.qualitypaper.co_tourism.mappers.comment.CommentMapperImplementation;
import com.qualitypaper.co_tourism.model.Comment;
import com.qualitypaper.co_tourism.repository.CommentRepository;
import com.qualitypaper.co_tourism.repository.PostRepository;
import com.qualitypaper.co_tourism.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapperImplementation commentMapperImplementation;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public CommentResponse createComment(CommentRequest commentRequest) {
        var comment = commentMapperImplementation.mapToComment(commentRequest);
        extractAndSetComment(comment, commentRequest);
        var response = saveAndMapCommentToCommentResponse(comment);
        return response;
    }

    private void extractAndSetComment(Comment comment, CommentRequest commentRequest) {
        comment.setUser(userRepository.findById(commentRequest.userId()).get());
        var post = postRepository.findById(commentRequest.postId()).get();
        comment.setPost(post);
        post.getComments().add(comment);
    }

    private CommentResponse saveAndMapCommentToCommentResponse(Comment comment) {
        var save = commentRepository.save(comment);
        var response = commentMapperImplementation.mapToCommentResponse(save);
        return response;
    }
}
