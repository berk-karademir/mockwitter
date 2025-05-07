package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Belirli bir tweet'e ait tüm yorumları bul
    List<Comment> findByTweet(Tweet tweet);

    // Belirli bir kullanıcıya ait tüm yorumları bul
    List<Comment> findByUser (User user);

}
