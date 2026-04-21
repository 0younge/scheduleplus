package com.scheduleplus.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    @NotBlank(message = "내용은 공백을 허용하지 않습니다.")
    @Size(max = 100, message = "내용은 100자 이내로 작성해야 합니다.")
    private final String content;

    public UpdateCommentRequest(String content) {
        this.content = content;
    }
}
