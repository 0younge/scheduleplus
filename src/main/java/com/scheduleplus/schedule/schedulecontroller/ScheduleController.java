package com.scheduleplus.schedule.schedulecontroller;

import com.scheduleplus.schedule.scheduledto.CreateScheduleRequest;
import com.scheduleplus.schedule.scheduledto.GetScheduleResponse;
import com.scheduleplus.schedule.scheduledto.UpdateScheduleRequest;
import com.scheduleplus.schedule.scheduleservice.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> createSchedule(@RequestBody CreateScheduleRequest request, HttpSession session) {
        if (null != session.getAttribute("sessionId")) {
            scheduleService.save(request, session);
        } else {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedule() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll());
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request, HttpSession session) {
        scheduleService.update(scheduleId, request, session);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
