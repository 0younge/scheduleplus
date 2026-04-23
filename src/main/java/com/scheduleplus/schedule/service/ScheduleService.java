package com.scheduleplus.schedule.service;

import com.scheduleplus.schedule.dto.CreateScheduleRequest;
import com.scheduleplus.schedule.dto.GetScheduleResponse;
import com.scheduleplus.schedule.dto.GetScheduleWrapperResponse;
import com.scheduleplus.schedule.dto.UpdateScheduleRequest;
import com.scheduleplus.schedule.entity.Schedule;
import com.scheduleplus.schedule.repository.ScheduleRepository;
import com.scheduleplus.common.UserAuthInfo;
import com.scheduleplus.user.entity.User;
import com.scheduleplus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    /**
     * 일정 저장
     * @param request 제목 내용
     * @param userAuthInfo 검증을 위한 세션 값
     */
    @Transactional
    public void save(CreateScheduleRequest request, UserAuthInfo userAuthInfo) {
        User user = userService.findUserByIdElseThrow(userAuthInfo.getUserId());

        scheduleRepository.save(new Schedule(request.getTitle(), request.getContent(), user));
    }

    /**
     * 전체 일정 조회
     * @param pageable 페이지네이션 할 정보
     * @return 페이지네이션 한 전체 일정 반환
     */
    @Transactional(readOnly = true)
    public GetScheduleWrapperResponse getAll(Pageable pageable) {
        Page<GetScheduleResponse> schedulePage = scheduleRepository.findAll(pageable)
                .map(schedule -> {
                    Long commentCount = (long) schedule.getComments().size();
                    return GetScheduleResponse.from(schedule, commentCount);
                });

        return GetScheduleWrapperResponse.from(schedulePage);
    }

    /**
     * 특정 일정 조회
     * @param scheduleId 조회할 일정 아이디
     * @return 특정 일정제목, 내용, 작성/수정일, 댓글 수, 작성자 반환
     */
    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = findScheduleByIdElseThrow(scheduleId);
        Long commentCount = (long) schedule.getComments().size();

        return GetScheduleResponse.from(schedule, commentCount);
    }

    /**
     * 일정 수정
     * @param scheduleId 수정할 일정 아이디
     * @param request 수정할 일정제목, 내용
     * @param userAuthInfo 검증을 위한 세션 값
     */
    @Transactional
    public void update(Long scheduleId, UpdateScheduleRequest request, UserAuthInfo userAuthInfo) {
        Schedule schedule = findScheduleByIdElseThrow(scheduleId);
        schedule.authorVerification(userAuthInfo.getName());

        schedule.updateSchedule(request.getTitle(), request.getContent());
    }

    /**
     * 일정 삭제
     * @param scheduleId 삭제할 일정 아이디
     * @param userAuthInfo 검증을 위한 세션 값
     */
    @Transactional
    public void delete(Long scheduleId, UserAuthInfo userAuthInfo) {
        Schedule schedule = findScheduleByIdElseThrow(scheduleId);
        schedule.authorVerification(userAuthInfo.getName());

        scheduleRepository.delete(schedule);
    }

    /**
     * 아이디로 일정을 찾고 예외를 던지는 메서드
     * @param scheduleId 찾아올 일정 아이디
     * @return 예외처리가 끝난 일정 반환
     */
    public Schedule findScheduleByIdElseThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 일정입니다."));

    }
}
