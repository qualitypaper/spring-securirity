package com.qualitypaper.co_tourism.repository;

import com.qualitypaper.co_tourism.model.Comment;
import com.qualitypaper.co_tourism.model.Post;
import com.qualitypaper.co_tourism.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentByUser(User user);
    Comment findCommentByPost(Post post);
    Comment deleteAllByPost(Post post);

    List<Comment> findAllByPostId(Long id);
}
