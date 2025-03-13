package com.BekoInc.mockwitter.mapper;

import com.BekoInc.mockwitter.dto.TweetResponse;
import com.BekoInc.mockwitter.dto.UserDTO;
import com.BekoInc.mockwitter.entity.Tweet;
import com.BekoInc.mockwitter.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {

    public TweetResponse toDTO(Tweet tweet) {
        if (tweet == null) {
            return null;
        }

        TweetResponse dto = new TweetResponse();
        dto.setId(tweet.getId());
        dto.setContent(tweet.getContent());
        dto.setPostDate(tweet.getPostDate());

        // User bilgilerini ekle
        if (tweet.getUser() != null) {
            dto.setUser(toUserDTO(tweet.getUser()));
        }

        // İlişkili koleksiyon sayılarını ekle
        dto.setLikesCount(tweet.getLikes() != null ? tweet.getLikes().size() : 0);
        dto.setLikesCount(tweet.getLikes() != null ? tweet.getLikes().size() : 0);

        dto.setCommentsCount(tweet.getComments() != null ? tweet.getComments().size() : 0);
        dto.setRetweetsCount(tweet.getRetweets() != null ? tweet.getRetweets().size() : 0);

        return dto;
    }

    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        // Aşağıdaki alanları ilerde eklersen: !!!
        // dto.setDisplayName(user.getDisplayName());
        // dto.setProfilePict ureUrl(user.getProfilePictureUrl());
        return dto;
    }
}