package com.scheduleplus.schedule.controller;

import com.scheduleplus.schedule.dto.CreateScheduleRequest;
import com.scheduleplus.schedule.dto.GetScheduleResponse;
import com.scheduleplus.schedule.dto.GetScheduleWrapperResponse;
import com.scheduleplus.schedule.dto.UpdateScheduleRequest;
import com.scheduleplus.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody @Valid CreateScheduleRequest request, HttpSession session) {
        if (null != session.getAttribute("sessionId")) {
            scheduleService.save(request, session);
        } else {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<GetScheduleWrapperResponse> getAllSchedule(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll(pageable));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@PathVariable Long scheduleId, @RequestBody @Valid UpdateScheduleRequest request, HttpSession session) {
        scheduleService.update(scheduleId, request, session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, HttpSession session) {
        scheduleService.delete(scheduleId, session);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
