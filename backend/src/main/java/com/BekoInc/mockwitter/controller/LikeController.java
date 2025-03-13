package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.LikeResponse;
import com.BekoInc.mockwitter.entity.Reply;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.repository.ReplyRepository;


import com.BekoInc.mockwitter.service.CommentLikeService;
import com.BekoInc.mockwitter.service.ReplyLikeService;
import com.BekoInc.mockwitter.service.TweetLikeService;
import com.BekoInc.mockwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final TweetLikeService tweetLikeService;
    private final CommentLikeService commentLikeService;
    private final ReplyLikeService replyLikeService;
    private final UserService userService;
    private ReplyRepository replyRepository;


    @Autowired
    public LikeController(CommentLikeService commentLikeService, TweetLikeService tweetLikeService, ReplyLikeService replyLikeService, UserService userService, ReplyRepository replyRepository) {
        this.commentLikeService = commentLikeService;
        this.tweetLikeService = tweetLikeService;
        this.replyLikeService = replyLikeService;
        this.userService = userService;
        this.replyRepository = replyRepository;
    }



    @PostMapping("/tweet/{tweetId}")
    public void likeTweet(@PathVariable Long tweetId, @AuthenticationPrincipal UserDetails userDetails) {
        validateUser(userDetails);
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        tweetLikeService.likeTweet(tweetId, user);
    }

    @DeleteMapping("/tweet/{tweetId}")
    public void undoLikeTweet(@PathVariable Long tweetId, @AuthenticationPrincipal UserDetails userDetails) {
        validateUser(userDetails);
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        tweetLikeService.undoLikeTweet(tweetId, user);
    }

    @PostMapping("/comment/{commentId}")
    public void likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        validateUser(userDetails);
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        commentLikeService.likeComment(commentId, user);
    }

    @DeleteMapping("/comment/{commentId}")
    public void undoLikeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        validateUser(userDetails);
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        commentLikeService.undoLikeComment(commentId, user);
    }

    @PostMapping("/reply/{replyId}")
    public  LikeResponse likeReply(@PathVariable Long replyId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new UsernameNotFoundException("Not an authorized action!");
        }
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();


        replyLikeService.likeReply(replyId, user);
        Reply reply = replyRepository.findById(replyId).orElseThrow();

        return new LikeResponse(reply.getContent(), HttpStatus.OK, HttpStatus.OK.value());


    }


    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<String> undoLikeReply(@PathVariable Long replyId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User must be authenticated");
        }
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        replyLikeService.undoLikeReply(replyId, user);


        return ResponseEntity.ok("Reply unlike successful");
    }




    private void validateUser(UserDetails userDetails) {
        if (userDetails == null || userDetails.getUsername() == null) {
            throw new RuntimeException("You must be logged in to perform this action.");
        }
    }
}
