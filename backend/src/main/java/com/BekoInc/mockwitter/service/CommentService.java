package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.dto.ReplyDTO;
import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.user.User;


public interface CommentService {
    Comment addComment(Comment comment);
    Comment updateComment(Long id, Comment comment, User user);
    void deleteComment(Long id, User user);
    Comment findById(Long id);
}