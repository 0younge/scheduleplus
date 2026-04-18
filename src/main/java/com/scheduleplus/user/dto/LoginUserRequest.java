package com.scheduleplus.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginUserRequest {

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Size(min = 8)
    private final String password;

    public LoginUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
