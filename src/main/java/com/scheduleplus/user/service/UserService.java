package com.scheduleplus.user.service;

import com.scheduleplus.common.SessionValue;
import com.scheduleplus.user.dto.*;
import com.scheduleplus.user.entity.User;
import com.scheduleplus.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 전체 유저 리스트 조회:
     * 유저 이름, 유저 생성/수정시간
     *
     * @return 위 정보를 포함한 유저 리스트
     */
    @Transactional(readOnly = true)
    public List<GetUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(GetUserResponse::from).toList();
    }

    /**
     * 특정 유저 조회: 유저 이름, 유저 이메일, 유저 생성/수정시간
     * @param userId 조회할 유저 아이디
     * @return 위 종보를 포함한 특정 유저 정보
     */
    @Transactional(readOnly = true)
    public GetOneUserResponse getOne(Long userId) {
        return GetOneUserResponse.from(getUser(userId));
    }

    /**
     * 유저 정보 수정
     * @param userId 수정할 유저 아이디
     * @param session 검증을 위한 세션
     * @param request 수정할 내용(유저이름, 이메일)
     */
    @Transactional
    public void update(Long userId, HttpSession session, UpdateUserRequest request) {
        if (authUser(userId, session)) {
            getUser(userId).userUpdate(request.getName(), request.getEmail());
        }
    }

    /**
     * 유저 삭제 기능
     * @param userId 삭제할 유저 아이디
     * @param session 검증을 위한 세션
     */
    @Transactional
    public void delete(Long userId, HttpSession session) {
        if (authUser(userId, session)) {
            userRepository.deleteById(userId);
        }
    }

    /**
     * 유저 아이디로 유저 반환 메서드
     * @param userId 반환할 유저 아이디
     * @return 예외처리를 마친 유저객체 반환
     */
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
    }

    /**
     * 특정 작업을 위한 검증 메서드
     * @param userId 작업을 할 유저 아이디
     * @param session 검증을 위한 세션
     * @return 검증 정보 반환
     */
    public boolean authUser(Long userId, HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        if (sessionValue == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 작업입니다.");
        } else if (!userId.equals(sessionValue.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 유저정보만 삭제가 가능합니다.");
        } else {
            return true;
        }
    }

}
