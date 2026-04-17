package com.scheduleplus.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    private final String title;
    private final String content;

    public UpdateScheduleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
