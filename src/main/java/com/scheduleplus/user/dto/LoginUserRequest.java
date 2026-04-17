package com.scheduleplus.user.dto;

import lombok.Getter;

@Getter
public class LoginUserRequest {

    private final String email;
    private final String password;

    public LoginUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
