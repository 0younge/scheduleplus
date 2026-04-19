package com.scheduleplus.auth.service;

import com.scheduleplus.common.PasswordEncoder;
import com.scheduleplus.auth.dto.CreateUserRequest;
import com.scheduleplus.common.SessionValue;
import com.scheduleplus.auth.dto.LoginUserRequest;
import com.scheduleplus.user.entity.User;
import com.scheduleplus.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(), request.getEmail(), encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public void login(LoginUserRequest request, HttpSession session) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
        boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (isMatch) {
            SessionValue sessionValue = new SessionValue(user.getUserId(), user.getName());
            session.setAttribute("sessionId", sessionValue);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
