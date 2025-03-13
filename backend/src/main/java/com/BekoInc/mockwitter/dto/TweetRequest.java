package com.BekoInc.mockwitter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetRequest {
    @Size(min = 1, max = 280, message = "Tweet must be between 1 and 280 characters!")
    @NotNull(message = "Tweet can not be null!")
    @NotBlank(message = "Tweet can not be empty!")
    private String content;
}