package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.dto.LikeResponse;
import com.BekoInc.mockwitter.entity.user.User;

public interface ReplyLikeService {
    LikeResponse likeReply(Long replyId, User user);
    void undoLikeReply(Long replyId, User user);
}
