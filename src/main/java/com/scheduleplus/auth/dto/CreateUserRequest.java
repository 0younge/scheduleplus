package com.scheduleplus.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank
    @Size(max = 5)
    private final String name;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Size(min = 8)
    private final String password;

    public CreateUserRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
