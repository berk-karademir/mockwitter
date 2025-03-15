package com.BekoInc.mockwitter.mapper;

import com.BekoInc.mockwitter.dto.RetweetResponse;
import com.BekoInc.mockwitter.dto.UserDTO;
import com.BekoInc.mockwitter.entity.Retweet;
import com.BekoInc.mockwitter.entity.user.User;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class RetweetMapper {

    private final TweetMapper tweetMapper;

    @Autowired
    public RetweetMapper(TweetMapper tweetMapper) {
        this.tweetMapper = tweetMapper;
    }

    public RetweetResponse toDTO(Retweet retweet) {
        if (retweet == null) {
            return null;
        }

        RetweetResponse dto = new RetweetResponse();
        dto.setId(retweet.getId());
        dto.setContent(retweet.getContent());
        dto.setPostDate(retweet.getRetweetDate());

        if (retweet.getUser() != null) {
            dto.setUser(toUserDTO(retweet.getUser()));
        }

        if (retweet.getTweet() != null) {
            dto.setTweet(tweetMapper.toDTO(retweet.getTweet()));
        }

        dto.setLikesCount(retweet.getRtLikes() != null ? retweet.getRtLikes().size() : 0);
        dto.setCommentsCount(retweet.getComments() != null ? retweet.getComments().size() : 0);

        return dto;
    }

    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
