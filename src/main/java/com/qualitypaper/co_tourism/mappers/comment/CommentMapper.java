package com.qualitypaper.co_tourism.mappers.comment;

import com.qualitypaper.co_tourism.controller.dto.CommentRequest;
import com.qualitypaper.co_tourism.controller.dto.CommentResponse;
import com.qualitypaper.co_tourism.model.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment mapToComment(CommentRequest request);
    CommentResponse mapToCommentResponse(Comment comment);
}
