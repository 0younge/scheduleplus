package com.scheduleplus.user.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.scheduleplus.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonPropertyOrder({"name", "createdAt", "modifiedAt"})
public class GetUserResponse {

    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private GetUserResponse(String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static GetUserResponse from(User user) {
        return new GetUserResponse(user.getName(),
                                   user.getCreatedAt(),
                                   user.getModifiedAt());
    }
}
