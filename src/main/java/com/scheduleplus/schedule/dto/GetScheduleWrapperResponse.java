package com.scheduleplus.schedule.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@JsonPropertyOrder({"schedules", "currentPage", "totalPages", "totalElements"})
public class GetScheduleWrapperResponse {

    private final List<GetScheduleResponse> schedules;
    private final int currentPage;
    private final int totalPages;
    private final long totalElements;

    private GetScheduleWrapperResponse(List<GetScheduleResponse> schedules, int currentPage, int totalPages, long totalElements) {
        this.schedules = schedules;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public static GetScheduleWrapperResponse from(Page<GetScheduleResponse> schedulePage) {
        return new GetScheduleWrapperResponse(
                schedulePage.getContent(),
                schedulePage.getNumber(),
                schedulePage.getTotalPages(),
                schedulePage.getTotalElements());
    }
}
