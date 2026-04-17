package com.scheduleplus.user.userdto;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private final String name;
    private final String email;
    private final String password;

    public CreateUserRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
