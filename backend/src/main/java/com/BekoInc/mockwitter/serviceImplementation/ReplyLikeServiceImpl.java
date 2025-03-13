package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.dto.LikeResponse;
import com.BekoInc.mockwitter.entity.Comment;
import com.BekoInc.mockwitter.entity.Reply;

import com.BekoInc.mockwitter.entity.ReplyLike;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.mapper.TweetMapper;
import com.BekoInc.mockwitter.repository.ReplyLikeRepository;
import com.BekoInc.mockwitter.repository.ReplyRepository;
import com.BekoInc.mockwitter.service.ReplyLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReplyLikeServiceImpl implements ReplyLikeService {

    private final ReplyLikeRepository replyLikeRepository;
    private final ReplyRepository replyRepository;
    private TweetMapper tweetMapper;


    @Autowired
    public ReplyLikeServiceImpl(ReplyLikeRepository replyLikeRepository, ReplyRepository replyRepository, TweetMapper tweetMapper) {
        this.replyLikeRepository = replyLikeRepository;
        this.replyRepository = replyRepository;
        this.tweetMapper = tweetMapper;
    }



    @Override
    public LikeResponse likeReply(Long replyId, User user) {
        if (user == null) {
            throw new RuntimeException("User must be authenticated to like a reply.");
        }

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found with id: " + replyId));


        if (!replyLikeRepository.existsByReplyAndUser(reply, user)) {
            ReplyLike replyLike = new ReplyLike();
            replyLike.setReply(reply);
            replyLike.setUser(user);
            replyLikeRepository.save(replyLike);
        }
//        TweetDTO tweetContainsComment = tweetMapper.toDTO(reply.getTweet());
        Comment comment = reply.getComment();
        return new LikeResponse(reply.getContent(), HttpStatus.OK, HttpStatus.OK.value());
    }


    @Override
    public void undoLikeReply(Long replyId, User user) {
        // Reply objesini veritabanından çek
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found with id: " + replyId));

        // Kullanıcının bu reply'i beğenip beğenmediğini kontrol et ve sil
        replyLikeRepository.deleteByReplyAndUser(reply, user);

    }
}
