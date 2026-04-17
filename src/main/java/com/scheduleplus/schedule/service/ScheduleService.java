package com.scheduleplus.schedule.service;

import com.scheduleplus.schedule.dto.CreateScheduleRequest;
import com.scheduleplus.schedule.dto.GetScheduleResponse;
import com.scheduleplus.schedule.dto.UpdateScheduleRequest;
import com.scheduleplus.schedule.entity.Schedule;
import com.scheduleplus.schedule.repository.ScheduleRepository;
import com.scheduleplus.user.dto.SessionValue;
import com.scheduleplus.user.entity.User;
import com.scheduleplus.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(CreateScheduleRequest request, HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        User user = userRepository.findById(sessionValue.getUserId()).orElseThrow(() -> new IllegalStateException("id로 조회되는 유저가 없습니다."));
        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), user);
        scheduleRepository.save(schedule);
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetScheduleResponse> dtos = new ArrayList<>();

        for (Schedule schedule : schedules) {
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt(),
                    schedule.getUser().getName());
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalStateException("없는 일정입니다."));
        return new GetScheduleResponse(
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                schedule.getUser().getName()
        );
    }

    @Transactional
    public void update(Long scheduleId, UpdateScheduleRequest request, HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalStateException("없는 일정입니다."));
        if (!schedule.getUser().getName().equals(sessionValue.getName())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
        schedule.updateSchedule(request.getTitle(), request.getContent());
    }

    @Transactional
    public void delete(Long scheduleId, HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalStateException("없는 일정입니다."));
        if (!schedule.getUser().getName().equals(sessionValue.getName())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
        scheduleRepository.delete(schedule);
    }
}
