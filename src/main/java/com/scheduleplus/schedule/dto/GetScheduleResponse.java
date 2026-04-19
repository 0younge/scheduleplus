package com.scheduleplus.schedule.dto;

import com.scheduleplus.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {

    private final String title;
    private final String content;
    private final Long commentCount;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
    private final String userName;

    public GetScheduleResponse(Schedule schedule, Long commentCount) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.commentCount = commentCount;
        this.createAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.userName = schedule.getUser().getName();
    }
}
