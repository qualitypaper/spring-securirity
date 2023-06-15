package com.qualitypaper.co_tourism.controller.dto;

import com.qualitypaper.co_tourism.model.Post;
import com.qualitypaper.co_tourism.model.user.User;
import lombok.Builder;

@Builder
public record CommentResponse(String content, Post post, User user) {
}
