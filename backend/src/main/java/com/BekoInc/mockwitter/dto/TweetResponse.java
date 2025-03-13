package com.BekoInc.mockwitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetResponse {
    private Long id;
    private String content;
    private LocalDateTime postDate;
    private UserDTO user;
    private int likesCount;
    private int dislikesCount;
    private int commentsCount;
    private int retweetsCount;
}