package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.Reply;
import com.BekoInc.mockwitter.entity.ReplyLike;
import com.BekoInc.mockwitter.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {

    // Reply ve User'a göre like kaydının olup olmadığını kontrol eder
    @Query("SELECT CASE WHEN COUNT(rl) > 0 THEN true ELSE false END FROM ReplyLike rl WHERE rl.reply = :reply AND rl.user = :user")

    boolean existsByReplyAndUser(@Param("reply")Reply reply,  @Param("user") User user);

    // Reply ve User'a göre like kaydını siler
    @Modifying
    @Transactional
    @Query("DELETE FROM ReplyLike rl WHERE rl.reply = :reply AND rl.user = :user")
    void deleteByReplyAndUser(Reply reply, User user);
}
