package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.CommentLike;
import com.BekoInc.mockwitter.entity.Like;
import com.BekoInc.mockwitter.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {


    // Comment ve User'a göre like kaydının olup olmadığını kontrol eder
    boolean existsByCommentAndUser(Comment comment, User user);

    // Comment ve User'a göre like kaydını siler
    void deleteByCommentAndUser(Comment comment, User user);

}
