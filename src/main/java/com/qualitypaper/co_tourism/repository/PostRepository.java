package com.qualitypaper.co_tourism.repository;

import com.qualitypaper.co_tourism.model.Post;
import com.qualitypaper.co_tourism.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostByUser(User user);
}
