package com.scheduleplus.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneUserResponse {

    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetOneUserResponse(String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
