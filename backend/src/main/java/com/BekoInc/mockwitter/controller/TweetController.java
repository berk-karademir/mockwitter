package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.TweetRequest;
import com.BekoInc.mockwitter.dto.TweetResponse;
import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.exception.MockwitterException;
import com.BekoInc.mockwitter.service.TweetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", 
              allowedHeaders = "*", 
              allowCredentials = "true")
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    private User getSessionUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new MockwitterException("You are not authorized", HttpStatus.UNAUTHORIZED);
        }
        return user;
    }

    private Tweet checkTweetExistence(Long id) {
        Tweet tweet = tweetService.findById(id);
        if (tweet == null) {
            throw new MockwitterException("Tweet not found", HttpStatus.NOT_FOUND);
        }
        return tweet;
    }

    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(@RequestBody TweetRequest request, HttpServletRequest httpRequest) {
        User user = getSessionUser(httpRequest);
        return ResponseEntity.ok(tweetService.createTweet(request, user));
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<List<TweetResponse>> getAllTweetsByUserId(@PathVariable Long userId, HttpServletRequest request) {
        User user = getSessionUser(request);
        List<TweetResponse> tweets = tweetService.getAllTweetsByUserId(userId);
        if (tweets.isEmpty()) {
            throw new MockwitterException("Your request completed successfully but this user has not posted any tweets yet.", HttpStatus.OK);
        }
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/findById/{tweetId}")
    public ResponseEntity<Tweet> getTweetById(@PathVariable("tweetId") Long id, HttpServletRequest request) {
        User user = getSessionUser(request);
        Tweet tweet = checkTweetExistence(id);
        return ResponseEntity.ok(tweet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetResponse> updateTweet(@PathVariable Long id, @RequestBody TweetRequest request, HttpServletRequest httpRequest) {
        User user = getSessionUser(httpRequest);
        Tweet existingTweet = checkTweetExistence(id);
        if (!existingTweet.getUser().equals(user)) {
            throw new MockwitterException("You are not authorized to update this tweet", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(tweetService.updateTweet(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id, HttpServletRequest request) {
        User user = getSessionUser(request);
        Tweet tweet = checkTweetExistence(id);
        if (!tweet.getUser().equals(user)) {
            throw new MockwitterException("You are not authorized to delete this tweet", HttpStatus.FORBIDDEN);
        }
        tweetService.deleteTweet(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByTweetId(@PathVariable Long id, HttpServletRequest request) {
        User user = getSessionUser(request);
        Tweet tweet = checkTweetExistence(id);
        List<Comment> comments = tweet.getComments();
        comments.sort(Comparator.comparing(Comment::getCommentDate));
        return ResponseEntity.ok(comments);
    }
}
