package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.RetweetRequest;
import com.BekoInc.mockwitter.dto.RetweetResponse;
import com.BekoInc.mockwitter.dto.TweetResponse;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.exception.MockwitterException;
import com.BekoInc.mockwitter.mapper.TweetMapper;
import com.BekoInc.mockwitter.service.TweetService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import com.BekoInc.mockwitter.service.UserService;
import com.BekoInc.mockwitter.service.RetweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class RetweetController {
    private UserService userService;
    private RetweetService retweetService;
    private TweetService tweetService;
    private TweetMapper tweetMapper;


    @Autowired
    public RetweetController(RetweetService retweetService, TweetMapper tweetMapper, TweetService tweetService, UserService userService) {
        this.retweetService = retweetService;
        this.tweetMapper = tweetMapper;
        this.tweetService = tweetService;
        this.userService = userService;
    }




    @PostMapping("/{tweetId}")
    public RetweetResponse createRetweet(@PathVariable Long tweetId, @AuthenticationPrincipal UserDetails userDetails, @RequestBody RetweetRequest req) {
        if (userDetails == null) {

        }

        // Kullanıcı bilgilerine erişebilirsiniz
        String username = userDetails.getUsername();

        // Kullanıcıyı bul
        // Kullanıcıyı bul
        User user = userService.loadUserByUsername(username);
        if (user == null) {

        }

        Tweet reTweetedTweet = tweetService.findById(tweetId);
        if (reTweetedTweet == null) {
            throw new MockwitterException("Tweet not found with id: " + tweetId, HttpStatus.NOT_FOUND);
        }

        // Tweet'i oluştur
        RetweetResponse retweetResponse = retweetService.createRetweet(req, user, tweetId);
        TweetResponse tweetResponse = tweetMapper.toDTO(reTweetedTweet);
        retweetResponse.setTweet(tweetResponse);
        return retweetResponse;


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRetweet(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.loadUserByUsername(userDetails.getUsername());
        // Retweet'i sil
        retweetService.deleteRetweet(id, user);

        return ResponseEntity.noContent().build();
    }
}
