package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.ReplyDTO;
import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.Reply;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.mapper.ReplyMapper;
import com.BekoInc.mockwitter.service.CommentService;
import com.BekoInc.mockwitter.service.ReplyService;
import com.BekoInc.mockwitter.service.TweetService;
import com.BekoInc.mockwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    private ReplyService replyService;
    private UserService userService;
    private ReplyMapper replyMapper;
    private TweetService tweetService;
    private CommentService commentService;

    @Autowired
    public ReplyController(CommentService commentService, ReplyMapper replyMapper, ReplyService replyService, TweetService tweetService, UserService userService) {
        this.commentService = commentService;
        this.replyMapper = replyMapper;
        this.replyService = replyService;
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @PostMapping("/{tweetId}/{commentId}")
    public ResponseEntity<Object> addReply(
        @PathVariable Long tweetId,
        @PathVariable Long commentId,
        @RequestBody ReplyDTO replyDTO,
        @AuthenticationPrincipal UserDetails userDetails) {
        
        // Get tweet
        Tweet tweet = tweetService.findById(tweetId);
        if (tweet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tweet not found with id: " + tweetId));
        }

        // Get comment and check if it belongs to the tweet
        Comment comment = commentService.findById(commentId);
        if (comment == null || !comment.getTweet().getId().equals(tweetId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Comment not found with id: " + commentId + " for tweet with id: " + tweetId));
        }

        // Get user
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User must be authenticated"));
        }

        User user = userService.loadUserByUsername(userDetails.getUsername());

        // Create reply
        Reply reply = new Reply();
        reply.setContent(replyDTO.getContent());
        reply.setUser(user);
        reply.setComment(comment);
        reply.setTweet(tweet);

        // Save reply
        Reply savedReply = replyService.addReply(tweetId, commentId, reply);

        return ResponseEntity.status(HttpStatus.CREATED).body(replyMapper.toDTO(savedReply));
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<ReplyDTO>> getRepliesByCommentId(@PathVariable Long commentId) {
        List<Reply> replies = replyService.getRepliesByCommentId(commentId);
        List<ReplyDTO> replyDTOs = replies.stream().map(replyMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(replyDTOs);
    }
}
