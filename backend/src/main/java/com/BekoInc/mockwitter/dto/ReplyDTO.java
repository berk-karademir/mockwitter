package com.BekoInc.mockwitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Long id;
    private String content;
    private LocalDateTime replyDate;
    private UserDTO user;
    private Long comment_id;
}