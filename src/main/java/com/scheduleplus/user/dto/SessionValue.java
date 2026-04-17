package com.scheduleplus.user.dto;

import lombok.Getter;

@Getter
public class SessionValue {
    private final Long userId;
    private final String name;

    public SessionValue(Long userId, String name) {
        this.userId = userId;
        this.name = name;

    }
}
