package com.BekoInc.mockwitter.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockwitterErrorResponse {

    private String message;
    private int status;
    private LocalDateTime timestamp;

}
