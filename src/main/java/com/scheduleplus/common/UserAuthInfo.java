package com.scheduleplus.common;

import lombok.Getter;

@Getter
public class UserAuthInfo {
    private final Long userId;
    private final String name;

    public UserAuthInfo(Long userId, String name) {
        this.userId = userId;
        this.name = name;

    }
}
