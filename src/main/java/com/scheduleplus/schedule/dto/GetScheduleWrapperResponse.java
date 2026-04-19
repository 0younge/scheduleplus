package com.scheduleplus.schedule.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class GetScheduleWrapperResponse {

    private final List<GetScheduleResponse> schedules;
    private final int currentPage;
    private final int totalPages;
    private final long totalElements;

    public GetScheduleWrapperResponse(Page<GetScheduleResponse> schedulePage) {
        this.schedules = schedulePage.getContent();
        this.currentPage = schedulePage.getNumber();
        this.totalPages = schedulePage.getTotalPages();
        this.totalElements = schedulePage.getTotalElements();
    }
}
