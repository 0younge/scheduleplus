package com.scheduleplus.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank(message = "이름은 공백을 허용하지 않습니다.")
    @Size(max = 5, message = "이름은 5글자 이내로 작성해야 합니다.")
    private final String name;

    @NotBlank(message = "메일은 공백을 허용하지 않습니다.")
    @Email(message = "메일 형식을 지켜야 합니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 공백을 허용하지 않습니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이여야 합니다.")
    private final String password;

    public CreateUserRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
