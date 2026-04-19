package com.scheduleplus.user.controller;

import com.scheduleplus.common.SessionValue;
import com.scheduleplus.user.dto.*;
import com.scheduleplus.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저 전체 조회
     * @return 유저 이름, 생성/수정일 반환
     */
    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUser() {
        return ResponseEntity.ok(userService.getAll());
    }

    /**
     * 특정 유저 조회
     * @param userId 조회하고자 하는 유저 아이디
     * @return 유저 이름, 이메일, 생성/수정일 반환
     */
    @GetMapping("/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getOne(userId));
    }

    /**
     * 유저 수정
     * @param userId 수정하고자 하는 유저 아이디
     * @param session 검증을 위한 세션
     * @param request 수정할 유저이름, 이메일
     * @return 상태코드
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId,
                                           HttpSession session,
                                           @RequestBody @Valid UpdateUserRequest request) {
        userService.update(userId, authSession(session), request);
        return ResponseEntity.ok().build();
    }

    /**
     * 유저 삭제
     * @param userId 삭제하고자 하는 유저 아이디
     * @param session 검증을 위한 세션
     * @return 상태코드
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, HttpSession session) {
        userService.delete(userId, authSession(session));
        return ResponseEntity.noContent().build();
    }

    /**
     * 로그인 검증 메서드
     * @param session 검증을 위한 세션
     * @return 세션 존재시 UserId 반환
     */
    public Long authSession(HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        if (sessionValue == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 작업입니다.");
        } else {
            return sessionValue.getUserId();
        }
    }

}
