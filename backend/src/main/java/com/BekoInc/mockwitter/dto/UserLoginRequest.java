package com.BekoInc.mockwitter.dto;


import lombok.Data;

@Data
public class UserLoginRequest {

    private String emailOrUsername;
    private String password;


}
