package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
}
