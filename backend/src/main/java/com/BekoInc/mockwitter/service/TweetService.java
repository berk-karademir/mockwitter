package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.dto.TweetRequest;
import com.BekoInc.mockwitter.dto.TweetResponse;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;

import java.util.List;

public interface TweetService {
    List<TweetResponse> getAllTweetsForFeed();
    List<TweetResponse> getAllTweetsByUserId(Long userId);
//    TweetResponse getTweetById(Long id);
    TweetResponse updateTweet(Long id, TweetRequest tweetRequest, User user);
    String deleteTweet(Long id, User user);
    TweetResponse createTweet(TweetRequest tweetRequest, User user);
    Tweet findById(Long tweetId);
}