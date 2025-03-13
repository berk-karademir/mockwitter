package com.BekoInc.mockwitter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikeResponse {


    private String replyContent;
    private HttpStatus httpStatus;
    private int statusCode;
}
