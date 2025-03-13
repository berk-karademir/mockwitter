package com.BekoInc.mockwitter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponse {
    private List<TweetResponse> tweets;
    private List<RetweetResponse> retweets;
}
