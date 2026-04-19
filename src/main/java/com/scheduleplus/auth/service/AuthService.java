package com.scheduleplus.auth.service;

import com.scheduleplus.common.PasswordEncoder;
import com.scheduleplus.auth.dto.CreateUserRequest;
import com.scheduleplus.common.SessionValue;
import com.scheduleplus.auth.dto.LoginUserRequest;
import com.scheduleplus.user.entity.User;
import com.scheduleplus.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입(유저 생성):
     * 생성할 유저의 정보를 받아 저장
     * @param request 생성할 유저의 이름, 이메일, 비밀번호
     */
    @Transactional
    public void save(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userRepository.save(new User(request.getName(), request.getEmail(), encodedPassword));
    }

    /**
     * 로그인: 유저의 이메일과 비밀번호를 일치하는지 검증
     * @param request 로그인할 유저의 이메일, 비밀번호
     * @return 세션에 저장할 값 반환
     */
    @Transactional(readOnly = true)
    public SessionValue login(LoginUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 유저입니다."));
        boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isMatch) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return new SessionValue(user.getUserId(), user.getName());
    }
}
