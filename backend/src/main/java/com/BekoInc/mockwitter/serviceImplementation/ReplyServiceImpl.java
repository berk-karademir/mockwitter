package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.Reply;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.repository.CommentRepository;
import com.BekoInc.mockwitter.repository.ReplyRepository;
import com.BekoInc.mockwitter.repository.UserRepository;
import com.BekoInc.mockwitter.service.ReplyService;
import com.BekoInc.mockwitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private ReplyRepository replyRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private TweetService tweetService;

    @Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository, CommentRepository commentRepository, UserRepository userRepository, TweetService tweetService) {
        this.replyRepository = replyRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tweetService = tweetService;
    }

    @Override
    public Reply addReply(Long tweetId, Long commentId, Reply reply) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        
        Tweet tweet = tweetService.findById(tweetId);
        if (tweet == null) {
            throw new RuntimeException("Tweet not found with id: " + tweetId);
        }

        reply.setComment(comment);
        reply.setTweet(tweet);
        return replyRepository.save(reply);
    }

    @Override
    public void deleteReply(Long replyId, User user) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found with id: " + replyId));
        if (!reply.getUser().equals(user)) {
            throw new SecurityException("User is not authorized to delete this reply");
        }
        replyRepository.delete(reply);
    }

    @Override
    public List<Reply> getRepliesByCommentId(Long commentId) {
        return replyRepository.findByCommentId(commentId);
    }
}
