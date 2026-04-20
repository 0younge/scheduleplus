package com.scheduleplus.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank(message = "이름은 공백을 허용하지 않습니다.")
    @Size(max = 5, message = "이름은 5글자 이내로 작성해야 합니다.")
    private final String name;

    @NotBlank(message = "메일은 공백을 허용하지 않습니다.")
    @Email(message = "메일 형식을 지켜야 합니다.")
    private final String email;

    public UpdateUserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
