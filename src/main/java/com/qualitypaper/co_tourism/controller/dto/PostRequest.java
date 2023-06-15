package com.qualitypaper.co_tourism.controller.dto;

import lombok.Builder;

@Builder
public record PostRequest(String title, String content, String imageUrl, String token){

}
