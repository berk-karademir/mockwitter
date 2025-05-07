package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.dto.RetweetRequest;
import com.BekoInc.mockwitter.dto.RetweetResponse;
import com.BekoInc.mockwitter.entity.user.User;

import java.util.List;

public interface RetweetService {

    List<RetweetResponse> getAllRetweetsForFeed();
    RetweetResponse createRetweet(RetweetRequest RTRequest, User user, Long tweetId);
    void deleteRetweet(Long id, User user);

}