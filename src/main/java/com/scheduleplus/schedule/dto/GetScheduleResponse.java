package com.scheduleplus.schedule.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.scheduleplus.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"title", "content", "commentCount", "createdAt", "modifiedAt", "userName"})
public class GetScheduleResponse {

    private final String title;
    private final String content;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String userName;


    private GetScheduleResponse(String title, String content, Long commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String userName) {
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
    }

    public static GetScheduleResponse from(Schedule schedule, Long commentCount) {
        return new GetScheduleResponse(
                schedule.getTitle(),
                schedule.getContent(),
                commentCount,
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                schedule.getUser().getName());
    }
}
