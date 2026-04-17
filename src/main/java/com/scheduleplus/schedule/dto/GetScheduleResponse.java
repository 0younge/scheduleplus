package com.scheduleplus.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {

    private final String title;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
    private final String userName;

    public GetScheduleResponse(String title, String content, LocalDateTime createAt, LocalDateTime modifiedAt, String userName) {
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
    }
}
