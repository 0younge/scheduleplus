package com.scheduleplus.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    @NotBlank
    @Size(max = 30)
    private final String title;

    @NotBlank
    private final String content;

    public UpdateScheduleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
