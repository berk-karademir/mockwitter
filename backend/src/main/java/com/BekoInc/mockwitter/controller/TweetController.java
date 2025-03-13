package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.TweetRequest;
import com.BekoInc.mockwitter.dto.TweetResponse;
import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.exception.*;
import com.BekoInc.mockwitter.service.TweetService;
import com.BekoInc.mockwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;

    @Autowired
    public TweetController(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(@RequestBody TweetRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new MockwitterException("userDetails == null çalıştı", HttpStatus.UNAUTHORIZED);
        }

        String username = userDetails.getUsername();
        User user = userService.loadUserByUsername(username);
        if (user == null) {
            throw new MockwitterException("USER == NULL ÇALIŞTI" , HttpStatus.NOT_FOUND);
        }

        TweetResponse tweetResponse = tweetService.createTweet(request, user);
        return ResponseEntity.ok(tweetResponse);
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<List<TweetResponse>> getAllTweetsByUserId(@PathVariable Long userId) {
        List<TweetResponse> tweets = tweetService.getAllTweetsByUserId(userId);
        if (tweets.isEmpty()) {
            throw new MockwitterException("Your request completed successfully but this user has not posted any tweets yet.", HttpStatus.OK);
       }
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/findById/{tweetId}")
    public ResponseEntity<Tweet> getTweetById(@PathVariable("tweetId") Long id) {
        Tweet tweet = tweetService.findById(id);
        if (tweet == null) {
            throw new MockwitterException("Can not find any tweet with id +" + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tweet);
    }

    @PutMapping("/{id}")
    public TweetResponse updateTweet(@PathVariable Long id, @RequestBody TweetRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new MockwitterException("userDetails == null çalıştı", HttpStatus.UNAUTHORIZED);
        }

        User user = userService.loadUserByUsername(userDetails.getUsername());
        if (user == null) {
            throw new MockwitterException("USER == NULL ÇALIŞTI" , HttpStatus.NOT_FOUND);
        }
        return tweetService.updateTweet(id, request, user);
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new MockwitterException("userDetails == null çalıştı", HttpStatus.UNAUTHORIZED);
        }

        User user = userService.loadUserByUsername(userDetails.getUsername());
        if (user == null) {
            throw new MockwitterException("USER == NULL ÇALIŞTI" , HttpStatus.NOT_FOUND);
        }

        Tweet tweet = tweetService.findById(id);
        if (tweet == null) {
                   throw new MockwitterException("Tweet not found with id " + id, HttpStatus.NOT_FOUND);
        }

        if (!tweet.getUser().equals(user)) {
            throw new MockwitterException("You are not allowed to perform this action." , HttpStatus.FORBIDDEN);
        }
        tweetService.deleteTweet(id, user);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByTweetId(@PathVariable Long id) {
        Tweet tweet = tweetService.findById(id);
        if (tweet == null) {
            throw new MockwitterException("Tweet not found with id " + id, HttpStatus.NOT_FOUND);
        }
        List<Comment> comments = tweet.getComments();
        comments.sort(Comparator.comparing(Comment::getCommentDate));
        return ResponseEntity.ok(comments);
    }
}
