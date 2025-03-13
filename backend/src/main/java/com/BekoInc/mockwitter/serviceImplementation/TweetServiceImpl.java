package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.dto.TweetRequest;
import com.BekoInc.mockwitter.dto.TweetResponse;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.exception.MockwitterException;
import com.BekoInc.mockwitter.mapper.TweetMapper;
import com.BekoInc.mockwitter.repository.TweetRepository;
import com.BekoInc.mockwitter.repository.UserRepository;
import com.BekoInc.mockwitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final TweetMapper tweetMapper;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository,
                            UserRepository userRepository,
                            TweetMapper tweetMapper) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.tweetMapper = tweetMapper;
    }

    @Override
    public TweetResponse createTweet(TweetRequest tweetRequest, User user) {
        if (user == null) {
                throw new MockwitterException("BAD CREDENTIALS!", HttpStatus.UNAUTHORIZED);
        }

        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequest.getContent());
        tweet.setUser(user);

        Tweet savedTweet = tweetRepository.save(tweet);
        return tweetMapper.toDTO(savedTweet);
    }

    @Override
    public List<TweetResponse> getAllTweetsForFeed() {

        List<Tweet> tweets = tweetRepository.findAll();
        if (tweets.isEmpty()) {
            throw new MockwitterException("Your request has completed successfully but there is no posted tweets yet.", HttpStatus.OK);
        }

        return tweets.stream()
                .map(tweetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> getAllTweetsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MockwitterException("User not found with id: " + userId, HttpStatus.NOT_FOUND));
        return tweetRepository.findAllByUserId(user.getId()).stream()
                .map(tweetMapper::toDTO)
                .collect(Collectors.toList());

    }

//    @Override
//    public TweetResponse getTweetById(Long id) {
//        Tweet tweet = tweetRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Tweet not found with 'ID: " + id + "'"));
//
//        return tweetMapper.toDTO(tweet);
//    }

    @Override
    public Tweet findById(Long tweetId) {
        Tweet foundTweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new MockwitterException("Tweet not found with id: " + tweetId, HttpStatus.NOT_FOUND));
        return foundTweet;
    }

    @Override
    public TweetResponse updateTweet(Long id, TweetRequest tweetRequest, User user) {
        Tweet existingTweet = tweetRepository.findById(id)
                .orElseThrow(() -> new MockwitterException("Tweet not found with ID " + id, HttpStatus.NOT_FOUND));

        if (!existingTweet.getUser().equals(user)) {
            throw new MockwitterException("YOU ARE NOT AUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        existingTweet.setContent(tweetRequest.getContent());
        existingTweet.setPostDate(LocalDateTime.now());
        Tweet updatedTweet = tweetRepository.save(existingTweet);
        return tweetMapper.toDTO(updatedTweet);
    }


    @Override
    public String deleteTweet(Long id, User user) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new MockwitterException("Tweet not found with ID " + id, HttpStatus.NOT_FOUND));

        if (!tweet.getUser().equals(user)) {
            throw new MockwitterException("YOU ARE NOT AUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        tweetRepository.delete(tweet);
        return "Tweet with id " + id + " has been deleted successfully.";
    }


}