package com.scheduleplus.user.userdto;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String name;
    private String email;
    private String password;

}
