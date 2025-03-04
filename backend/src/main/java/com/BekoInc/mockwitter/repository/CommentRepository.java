package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
