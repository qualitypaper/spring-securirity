package com.qualitypaper.co_tourism.mappers.post;

import com.qualitypaper.co_tourism.controller.dto.PostRequest;
import com.qualitypaper.co_tourism.controller.dto.PostResponse;
import com.qualitypaper.co_tourism.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post mapToPost(PostRequest request);
    PostResponse mapToPostResponse(Post post);
}
