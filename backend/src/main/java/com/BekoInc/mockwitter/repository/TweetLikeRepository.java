package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.TweetLike;
import com.BekoInc.mockwitter.entity.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface TweetLikeRepository extends JpaRepository<TweetLike, Long> {



    // JPQL sorgusu ile Tweet ve User'a göre like kaydının olup olmadığını kontrol eder
    @Query("SELECT CASE WHEN COUNT(tl) > 0 THEN true ELSE false END FROM TweetLike tl WHERE tl.tweet = :tweet AND tl.user = :user")
    boolean existsByTweetAndUser(@Param("tweet") Tweet tweet, @Param("user") User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM TweetLike tl WHERE tl.tweet = :tweet AND tl.user = :user")
    void deleteByTweetAndUser(@Param("tweet") Tweet tweet, @Param("user") User user);
}
