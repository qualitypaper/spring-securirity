package com.qualitypaper.co_tourism.repository;

import com.qualitypaper.co_tourism.model.user.Review;
import com.qualitypaper.co_tourism.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

    Review findReviewByUser(User user);
}
