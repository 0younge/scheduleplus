package com.scheduleplus.comment.dto;

import com.scheduleplus.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponse {

    private final Long userId;
    private final Long scheduleId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private GetCommentResponse(Long userId, Long scheduleId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static GetCommentResponse from(Comment comment) {
        return new GetCommentResponse(
                comment.getUser().getUserId(),
                comment.getSchedule().getScheduleId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }
}
