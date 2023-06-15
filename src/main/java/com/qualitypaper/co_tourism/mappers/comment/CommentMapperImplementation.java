package com.qualitypaper.co_tourism.mappers.comment;

import com.qualitypaper.co_tourism.controller.dto.CommentRequest;
import com.qualitypaper.co_tourism.controller.dto.CommentResponse;
import com.qualitypaper.co_tourism.model.Comment;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Component
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-19T22:03:58+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
public class CommentMapperImplementation implements CommentMapper{
    @Override
    public Comment mapToComment(CommentRequest request) {
        var comment = Comment.builder()
                .content(request.content())
                .build();
        return comment;
    }

    @Override
    public CommentResponse mapToCommentResponse(Comment comment) {
        var commentResponse = CommentResponse.builder()
                .content(comment.getContent())
                .user(comment.getUser())
                .post(comment.getPost())
                .build();
        return commentResponse;
    }
}
