package com.scheduleplus.schedule.scheduleservice;

import com.scheduleplus.schedule.scheduledto.CreateScheduleRequest;
import com.scheduleplus.schedule.scheduledto.GetScheduleResponse;
import com.scheduleplus.schedule.scheduledto.UpdateScheduleRequest;
import com.scheduleplus.schedule.scheduleentity.Schedule;
import com.scheduleplus.schedule.schedulerepository.ScheduleRepository;
import com.scheduleplus.user.userdto.SessionValue;
import com.scheduleplus.user.userentity.User;
import com.scheduleplus.user.userrepository.UserRepository;
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
    public void update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalStateException("없는 일정입니다."));
        schedule.updateSchedule(request.getTitle(), request.getContent());
    }
}
