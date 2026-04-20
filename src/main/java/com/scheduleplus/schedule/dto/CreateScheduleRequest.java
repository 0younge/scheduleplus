package com.scheduleplus.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    @NotBlank(message = "제목은 공백을 허용하지 않습니다.")
    @Size(max = 30, message = "제목은 30자 이내로 작성해야 합니다.")
    private final String title;

    @NotBlank(message = "내용은 공백을 허용하지 않습니다.")
    private final String content;

    public CreateScheduleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
