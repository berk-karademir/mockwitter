package com.BekoInc.mockwitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RetweetResponse {

    private Long id;
    private TweetResponse tweet;
    private String content;
    private LocalDateTime postDate;
    private UserDTO user;
    private int likesCount;
    private int dislikeCount;
    private int commentsCount;

}
