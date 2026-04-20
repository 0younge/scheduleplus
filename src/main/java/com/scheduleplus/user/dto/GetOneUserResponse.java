package com.scheduleplus.user.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.scheduleplus.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"name", "email", "createdAt", "modifiedAt"})
public class GetOneUserResponse {

    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private GetOneUserResponse(String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static GetOneUserResponse from(User user) {
        return new GetOneUserResponse(
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());
    }
}
