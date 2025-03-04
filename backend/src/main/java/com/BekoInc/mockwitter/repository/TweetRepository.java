package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long>{
}
