package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.CommentDTO;
import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.exception.MockwitterException;
import com.BekoInc.mockwitter.service.CommentService;
import com.BekoInc.mockwitter.service.TweetService;
import com.BekoInc.mockwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;
    private UserService userService;
    private TweetService tweetService;

    @Autowired
    public CommentController(CommentService commentService, TweetService tweetService, UserService userService) {
        this.commentService = commentService;
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @PostMapping("/{tweetId}")
    public CommentDTO addComment(@PathVariable Long tweetId, @RequestBody CommentDTO commentDTO, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.loadUserByUsername(userDetails.getUsername());
        if (user == null) {
                throw new MockwitterException("YOU ARE NOT AUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        Tweet tweet = tweetService.findById(tweetId);
        if (tweet == null) {
            throw new MockwitterException("Tweet can not found with id " + tweetId, HttpStatus.NOT_FOUND);
        }

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setTweet(tweet);
        comment.setUser(user);

        Comment savedComment = commentService.addComment(comment);
        CommentDTO savedCommentDTO = new CommentDTO(savedComment.getId(), savedComment.getContent(), savedComment.getCommentDate(),
                 savedComment.getTweet().getId());
        return savedCommentDTO;
    }

    @PutMapping("/{id}")
    public CommentDTO updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.loadUserByUsername(userDetails.getUsername());
        if (user == null) {
            throw new MockwitterException("YOU ARE NOT AUTHORIZED", HttpStatus.UNAUTHORIZED);

        }

        Comment existingComment = commentService.findById(id);
        if(!existingComment.getUser().equals(user)) {
            throw new MockwitterException("YOU ARE NOT AUTHORIZED", HttpStatus.UNAUTHORIZED);
        }



        existingComment.setContent(commentDTO.getContent());
        Comment updatedComment = commentService.updateComment(id, existingComment, user);
        CommentDTO updatedCommentDTO = new CommentDTO(updatedComment.getId(), updatedComment.getContent(), updatedComment.getCommentDate(),  updatedComment.getTweet().getId());
        return updatedCommentDTO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.loadUserByUsername(userDetails.getUsername());
        if (user == null) {
            throw new MockwitterException("YOU ARE NOT AUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        commentService.deleteComment(id, user);

        return ResponseEntity.noContent().build();

    }
}
