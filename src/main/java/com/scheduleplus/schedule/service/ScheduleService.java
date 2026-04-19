package com.scheduleplus.schedule.service;

import com.scheduleplus.comment.repository.CommentRepository;
import com.scheduleplus.comment.service.CommentService;
import com.scheduleplus.schedule.dto.CreateScheduleRequest;
import com.scheduleplus.schedule.dto.GetScheduleResponse;
import com.scheduleplus.schedule.dto.GetScheduleWrapperResponse;
import com.scheduleplus.schedule.dto.UpdateScheduleRequest;
import com.scheduleplus.schedule.entity.Schedule;
import com.scheduleplus.schedule.repository.ScheduleRepository;
import com.scheduleplus.common.SessionValue;
import com.scheduleplus.user.entity.User;
import com.scheduleplus.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Transactional
    public void save(CreateScheduleRequest request, HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        User user = userService.getUser(sessionValue.getUserId());
        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), user);
        scheduleRepository.save(schedule);
    }

    @Transactional(readOnly = true)
    public GetScheduleWrapperResponse getAll(Pageable pageable) {
        Page<GetScheduleResponse> schedulePage = scheduleRepository.findAll(pageable)
                .map(schedule -> {
                    Long commentCount = commentRepository.countBySchedule(schedule);
                    return new GetScheduleResponse(schedule, commentCount);
                });
        return new GetScheduleWrapperResponse(schedulePage);
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = getSchedule(scheduleId);
        Long commentCount = commentRepository.countBySchedule(schedule);
        return new GetScheduleResponse(schedule, commentCount);
    }

    @Transactional
    public void update(Long scheduleId, UpdateScheduleRequest request, HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        Schedule schedule = getSchedule(scheduleId);
        if (!schedule.getUser().getName().equals(sessionValue.getName())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
        schedule.updateSchedule(request.getTitle(), request.getContent());
    }

    @Transactional
    public void delete(Long scheduleId, HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        Schedule schedule = getSchedule(scheduleId);
        if (!schedule.getUser().getName().equals(sessionValue.getName())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
        scheduleRepository.delete(schedule);
    }

    public Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalStateException("없는 일정입니다."));
    }
}
