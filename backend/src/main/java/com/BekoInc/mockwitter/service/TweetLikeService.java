package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.entity.user.User;

public interface TweetLikeService {

    void likeTweet(Long tweetId, User user);
    void undoLikeTweet(Long tweetId, User user);
}
