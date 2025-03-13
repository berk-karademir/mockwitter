package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.entity.user.User;

public interface CommentLikeService {

    void likeComment(Long commentId, User user);
    void undoLikeComment(Long commentId, User user);
}
