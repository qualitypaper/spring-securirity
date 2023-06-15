package com.qualitypaper.co_tourism.controller.dto;

import lombok.Builder;

@Builder
public record PostResponse(String title, String content, String imageUrl) {
}
