package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.CommentDTO;
import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.exception.MockwitterException;
import com.BekoInc.mockwitter.service.CommentService;
import com.BekoInc.mockwitter.service.TweetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173", 
              allowedHeaders = "*", 
              allowCredentials = "true")
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;
    private TweetService tweetService;

    @Autowired
    public CommentController(CommentService commentService, TweetService tweetService) {
        this.commentService = commentService;
        this.tweetService = tweetService;
    }

    @PostMapping("/{tweetId}")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long tweetId, @RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new MockwitterException("You are not authorized", HttpStatus.UNAUTHORIZED);
        }

        Tweet tweet = tweetService.findById(tweetId);
        if (tweet == null) {
            throw new MockwitterException("Tweet not found with id " + tweetId, HttpStatus.NOT_FOUND);
        }

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setTweet(tweet);
        comment.setUser(user);

        Comment savedComment = commentService.addComment(comment);
        CommentDTO savedCommentDTO = new CommentDTO(savedComment.getId(), savedComment.getContent(), savedComment.getCommentDate(),
                 savedComment.getTweet().getId());
        return ResponseEntity.ok(savedCommentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new MockwitterException("You are not authorized", HttpStatus.UNAUTHORIZED);
        }

        Comment existingComment = commentService.findById(id);
        if (existingComment == null) {
            throw new MockwitterException("Comment not found", HttpStatus.NOT_FOUND);
        }

        if (!existingComment.getUser().equals(user)) {
            throw new MockwitterException("You are not authorized to update this comment", HttpStatus.FORBIDDEN);
        }

        existingComment.setContent(commentDTO.getContent());
        Comment updatedComment = commentService.updateComment(id, existingComment, user);
        CommentDTO updatedCommentDTO = new CommentDTO(updatedComment.getId(), updatedComment.getContent(), updatedComment.getCommentDate(),  updatedComment.getTweet().getId());
        return ResponseEntity.ok(updatedCommentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new MockwitterException("You are not authorized", HttpStatus.UNAUTHORIZED);
        }

        Comment existingComment = commentService.findById(id);
        if (existingComment == null) {
            throw new MockwitterException("Comment not found", HttpStatus.NOT_FOUND);
        }

        if (!existingComment.getUser().equals(user)) {
            throw new MockwitterException("You are not authorized to delete this comment", HttpStatus.FORBIDDEN);
        }

        commentService.deleteComment(id, user);
        return ResponseEntity.noContent().build();
    }
}
