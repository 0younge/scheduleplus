package com.scheduleplus.schedule.scheduleservice;

import com.scheduleplus.schedule.schedulerepository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
}
