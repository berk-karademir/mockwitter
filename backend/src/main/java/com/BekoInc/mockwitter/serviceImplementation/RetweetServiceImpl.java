package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.dto.RetweetRequest;
import com.BekoInc.mockwitter.dto.RetweetResponse;
import com.BekoInc.mockwitter.entity.Retweet;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.exception.MockwitterException;
import com.BekoInc.mockwitter.mapper.RetweetMapper;
import com.BekoInc.mockwitter.mapper.TweetMapper;
import com.BekoInc.mockwitter.repository.RetweetRepository;
import com.BekoInc.mockwitter.repository.TweetRepository;
import com.BekoInc.mockwitter.service.RetweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RetweetServiceImpl implements RetweetService {

    private RetweetRepository retweetRepository;
    private TweetRepository tweetRepository;
    private RetweetMapper retweetMapper;
    private TweetMapper tweetMapper;


    @Autowired
    public RetweetServiceImpl(RetweetMapper retweetMapper, RetweetRepository retweetRepository, TweetMapper tweetMapper, TweetRepository tweetRepository) {
        this.retweetMapper = retweetMapper;
        this.retweetRepository = retweetRepository;
        this.tweetMapper = tweetMapper;
        this.tweetRepository = tweetRepository;
    }


    @Override
    public List<RetweetResponse> getAllRetweetsForFeed() {
        List<Retweet> retweets = retweetRepository.findAll();

        // BOŞSA EXCEPTION ATMA
        if (retweets.isEmpty()) {
            return Collections.emptyList();
        }

        return retweets.stream()
                .map(retweetMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public RetweetResponse createRetweet(RetweetRequest RTRequest, User user, Long tweetId) {
        if (user == null) {
            throw new MockwitterException("reTweet must be associated with a user", HttpStatus.UNAUTHORIZED);
        }
        Tweet tweetToRetweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new MockwitterException("Tweet(to retweet) not found with id " + tweetId, HttpStatus.NOT_FOUND));



        Retweet retweet = new Retweet();
        retweet.setContent(RTRequest.getContent());
        retweet.setTweet(tweetToRetweet);
        retweet.setUser(user);
        Retweet savedRetweet = retweetRepository.save(retweet);
        return retweetMapper.toDTO(savedRetweet);

    }



@Override
public void deleteRetweet(Long id, User user) {
    Retweet retweet = retweetRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Retweet not found with id: " + id));
    if (!retweet.getUser().equals(user)) {
        throw new MockwitterException("User is not authorized to delete this retweet", HttpStatus.FORBIDDEN);
    }
    Tweet tweet = retweet.getTweet();
    tweet.getRetweets().remove(retweet);
    tweetRepository.save(tweet);
    retweetRepository.delete(retweet);
}

}
