package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.exception.MockwitterException;
import com.BekoInc.mockwitter.repository.CommentRepository;
import com.BekoInc.mockwitter.repository.ReplyRepository;
import com.BekoInc.mockwitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setCommentDate(LocalDateTime.now()); // Yorumun tarihini ayarla
        return commentRepository.save(comment);
    }


    @Override
    public Comment updateComment(Long id, Comment comment, User user) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new MockwitterException("Comment not found with id: " + id, HttpStatus.NOT_FOUND));
        if (existingComment.getUser ().equals(user)) {
            existingComment.setContent(comment.getContent());
            return commentRepository.save(existingComment);
        }
        return null;
    }

    @Override
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new MockwitterException("Comment not found with id: " + id, HttpStatus.NOT_FOUND));
        if (!(comment.getUser().equals(user))) {
            throw new MockwitterException("You are not authorized to delete this comment!", HttpStatus.FORBIDDEN);
        }
        commentRepository.delete(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new MockwitterException("Comment not found with id: " + id, HttpStatus.NOT_FOUND));
    }




}