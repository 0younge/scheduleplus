package com.scheduleplus.schedule.schedulecontroller;

import com.scheduleplus.schedule.scheduledto.CreateScheduleRequest;
import com.scheduleplus.schedule.scheduleservice.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
