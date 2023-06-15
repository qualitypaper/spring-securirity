package com.qualitypaper.co_tourism.controller.dto;

import lombok.Builder;

@Builder
public record CommentRequest(String content, Long postId, Long userId)  {
}
