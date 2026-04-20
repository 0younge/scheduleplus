package com.scheduleplus.schedule.controller;

import com.scheduleplus.common.BaseController;
import com.scheduleplus.common.SessionValue;
import com.scheduleplus.schedule.dto.CreateScheduleRequest;
import com.scheduleplus.schedule.dto.GetScheduleResponse;
import com.scheduleplus.schedule.dto.GetScheduleWrapperResponse;
import com.scheduleplus.schedule.dto.UpdateScheduleRequest;
import com.scheduleplus.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController extends BaseController {

    private final ScheduleService scheduleService;

    /**
     * 일정 추가
     * @param request 일정 추가를 위한 일정제목, 내용
     * @param session 검증을 위한 세션
     * @return 상태코드
     */
    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody @Valid CreateScheduleRequest request, HttpSession session) {
        scheduleService.save(request, authSession(session));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 일정 전체 조회
     * @param page 조회할 페이지
     * @param size 전체 조회시 나올 일정 수
     * @return 페이지네이션이 된 일정 목록
     */
    @GetMapping
    public ResponseEntity<GetScheduleWrapperResponse> getAllSchedule(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        return ResponseEntity.ok().body(scheduleService.getAll(pageable));
    }

    /**
     * 특정 일정 조회
     * @param scheduleId 조회할 일정 아이디
     * @return 특정 일정 반환
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok().body(scheduleService.getOne(scheduleId));
    }

    /**
     * 일정 수정
     * @param scheduleId 수정할 일정 아이디
     * @param request 수정할 제목, 내용
     * @param session 검증을 위한 세션
     * @return 상태코드
     */
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody @Valid UpdateScheduleRequest request,
            HttpSession session
    ) {
        scheduleService.update(scheduleId, request, authSession(session));
        return ResponseEntity.ok().build();
    }

    /**
     * 일정 삭제
     * @param scheduleId 삭제할 일정 아이디
     * @param session 검증을 위한 세션
     * @return 상태코드
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, HttpSession session) {
        scheduleService.delete(scheduleId, authSession(session));
        return ResponseEntity.noContent().build();
    }

}
