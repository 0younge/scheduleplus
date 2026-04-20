package com.scheduleplus.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank(message = "내용은 공백을 허용하지 않습니다.")
    @Size(max = 100)
    private final String content;

    public CreateCommentRequest(String content) {
        this.content = content;
    }
}
