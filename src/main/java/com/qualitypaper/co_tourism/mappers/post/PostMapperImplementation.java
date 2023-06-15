package com.qualitypaper.co_tourism.mappers.post;

import com.qualitypaper.co_tourism.controller.dto.PostRequest;
import com.qualitypaper.co_tourism.controller.dto.PostResponse;
import com.qualitypaper.co_tourism.model.Post;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Component
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-19T22:03:58+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
public class PostMapperImplementation implements PostMapper{


    @Override
    public Post mapToPost(PostRequest request) {
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .imageUrl(request.imageUrl())
                .build();
        return post;
    }

    @Override
    public PostResponse mapToPostResponse(Post post) {
        PostResponse postResponse = PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .build();
        return postResponse;
    }
}
