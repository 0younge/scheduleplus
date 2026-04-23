package com.scheduleplus.user.service;

import com.scheduleplus.common.UserAuthInfo;
import com.scheduleplus.user.dto.*;
import com.scheduleplus.user.entity.User;
import com.scheduleplus.user.repository.UserRepository;
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
        return GetOneUserResponse.from(findUserByIdElseThrow(userId));
    }

    /**
     * 유저 정보 수정
     * @param userId 수정할 유저 아이디
     * @param userAuthInfo 검증을 위한 세션 값
     * @param request 수정할 내용(유저이름, 이메일)
     */
    @Transactional
    public void update(Long userId, UserAuthInfo userAuthInfo, UpdateUserRequest request) {
        User user = findUserByIdElseThrow(userId);
        user.userIdVerification(userAuthInfo.getUserId());

        user.userUpdate(request.getName(), request.getEmail());
    }

    /**
     * 유저 삭제 기능
     * @param userId 삭제할 유저 아이디
     * @param userAuthInfo 검증을 위한 세션 값
     */
    @Transactional
    public void delete(Long userId, UserAuthInfo userAuthInfo) {
        User user = findUserByIdElseThrow(userId);
        user.userIdVerification(userAuthInfo.getUserId());

        userRepository.delete(user);
    }

    /**
     * 유저 아이디로 유저 반환 메서드
     * @param userId 반환할 유저 아이디
     * @return 예외처리를 마친 유저객체 반환
     */
    public User findUserByIdElseThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));
    }

}
