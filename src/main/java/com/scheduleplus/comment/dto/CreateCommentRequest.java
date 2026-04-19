package com.scheduleplus.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank
    @Size(max = 100)
    private final String content;

    public CreateCommentRequest(String content) {
        this.content = content;
    }
}
