package com.scheduleplus.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    @NotBlank
    @Size(max = 100)
    private final String content;

    public UpdateCommentRequest(String content) {
        this.content = content;
    }
}
