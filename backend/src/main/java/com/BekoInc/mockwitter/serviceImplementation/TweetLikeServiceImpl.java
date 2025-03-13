package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.TweetLike;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.repository.TweetLikeRepository;
import com.BekoInc.mockwitter.repository.TweetRepository;
import com.BekoInc.mockwitter.service.TweetLikeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetLikeServiceImpl implements TweetLikeService {

    private final TweetLikeRepository tweetLikeRepository;
    private final TweetRepository tweetRepository;

    @Autowired
    public TweetLikeServiceImpl(TweetLikeRepository tweetLikeRepository, TweetRepository tweetRepository) {
        this.tweetLikeRepository = tweetLikeRepository;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public void likeTweet(Long tweetId, User user) {
        // Tweet objesini veritabanından çek
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found with id: " + tweetId));

        // Kullanıcının daha önce bu tweet'i beğenip beğenmediğini kontrol et
        if (!tweetLikeRepository.existsByTweetAndUser(tweet, user)) {
            TweetLike tweetLike = new TweetLike();
            tweetLike.setTweet(tweet);
            tweetLike.setUser(user);
            tweetLikeRepository.save(tweetLike);
        }
    }

    @Override
    @Transactional
    public void undoLikeTweet(Long tweetId, User user){
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found with id: " + tweetId));

        if (tweetLikeRepository.existsByTweetAndUser(tweet, user)) {
            tweetLikeRepository.deleteByTweetAndUser(tweet, user);
        } else {
            throw new RuntimeException("You cannot undo a like tweet you haven't liked.");
        }
    }
}
