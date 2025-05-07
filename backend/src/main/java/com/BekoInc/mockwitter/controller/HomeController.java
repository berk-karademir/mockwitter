package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.TweetResponse;
import com.BekoInc.mockwitter.service.TweetService;
import com.BekoInc.mockwitter.service.RetweetService;
import com.BekoInc.mockwitter.dto.FeedResponse;
import com.BekoInc.mockwitter.dto.RetweetResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/home")
public class HomeController {

    private final TweetService tweetService;
    private final RetweetService retweetService;

    @Autowired
    public HomeController(TweetService tweetService, RetweetService retweetService) {
        this.tweetService = tweetService;
        this.retweetService = retweetService;
    }

    @GetMapping
    public FeedResponse getAllTweetsForFeed() {
        List<TweetResponse> tweets = tweetService.getAllTweetsForFeed();
        List<RetweetResponse> retweets = retweetService.getAllRetweetsForFeed();

        return new FeedResponse(tweets, retweets);
    }
}
