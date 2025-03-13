package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.entity.Reply;
import com.BekoInc.mockwitter.entity.user.User;


import java.util.List;

public interface ReplyService {
    Reply addReply(Long tweetId, Long commentId, Reply reply);
    void deleteReply(Long replyId, User user);
    List<Reply> getRepliesByCommentId(Long commentId);
}