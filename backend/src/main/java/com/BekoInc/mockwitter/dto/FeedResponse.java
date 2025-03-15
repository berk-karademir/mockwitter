package com.BekoInc.mockwitter.dto;

import java.util.List;

public class FeedResponse {
    private List<TweetResponse> tweets;
    private List<RetweetResponse> retweets;

    public FeedResponse(List<TweetResponse> tweets, List<RetweetResponse> retweets) {
        this.tweets = tweets;
        this.retweets = retweets;
    }

    public List<TweetResponse> getTweets() {
        return tweets;
    }

    public void setTweets(List<TweetResponse> tweets) {
        this.tweets = tweets;
    }

    public List<RetweetResponse> getRetweets() {
        return retweets;
    }

    public void setRetweets(List<RetweetResponse> retweets) {
        this.retweets = retweets;
    }
}
