package com.scheduleplus.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    private final String title;
    private final String content;

    public CreateScheduleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
