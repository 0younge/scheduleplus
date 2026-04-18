package com.scheduleplus.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank
    @Size(max = 5)
    private final String name;

    @NotBlank
    @Email
    private final String email;

    public UpdateUserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
