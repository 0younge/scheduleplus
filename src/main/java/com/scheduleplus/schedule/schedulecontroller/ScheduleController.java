package com.scheduleplus.schedule.schedulecontroller;

import com.scheduleplus.schedule.scheduleservice.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
}
