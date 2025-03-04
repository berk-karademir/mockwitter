package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.User;

import java.util.List;

public interface TweetService {

    List<Tweet> getAllTweetsById(User user);
}
