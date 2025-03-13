package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.TweetResponse;
import com.BekoInc.mockwitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/home")
public class HomeController {

    private final TweetService tweetService;

    @Autowired
    public HomeController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public List<TweetResponse> getAllTweetsForFeed() {
        return tweetService.getAllTweetsForFeed();
    }
}
