package com.scheduleplus.schedule.scheduleservice;

import com.scheduleplus.schedule.scheduledto.CreateScheduleRequest;
import com.scheduleplus.schedule.scheduleentity.Schedule;
import com.scheduleplus.schedule.schedulerepository.ScheduleRepository;
import com.scheduleplus.user.userdto.SessionValue;
import com.scheduleplus.user.userentity.User;
import com.scheduleplus.user.userrepository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
