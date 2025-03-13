package com.BekoInc.mockwitter.mapper;

import com.BekoInc.mockwitter.dto.RetweetResponse;
import com.BekoInc.mockwitter.dto.UserDTO;
import com.BekoInc.mockwitter.entity.Retweet;
import com.BekoInc.mockwitter.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class RetweetMapper {



    public RetweetResponse toDTO(Retweet retweet) {
        if (retweet == null) {
            return null;
        }

        RetweetResponse dto = new RetweetResponse();

        dto.setId(retweet.getId());
        dto.setContent(retweet.getContent());
        dto.setPostDate(retweet.getRetweetDate());
        // User bilgilerini ekle
        if (retweet.getUser() != null) {
            dto.setUser(toUserDTO(retweet.getUser()));
        }

        // Set the original tweet's ID


        // İlişkili koleksiyon sayılarını ekle

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
        // Aşağıdaki alanlar User sınıfınızda var ise ekle
        // dto.setDisplayName(user.getDisplayName());
        // dto.setProfilePictureUrl(user.getProfilePictureUrl());
        return dto;
    }
}
