package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
