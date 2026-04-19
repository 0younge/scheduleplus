package com.scheduleplus.user.service;

import com.scheduleplus.common.PasswordEncoder;
import com.scheduleplus.common.SessionValue;
import com.scheduleplus.user.dto.*;
import com.scheduleplus.user.entity.User;
import com.scheduleplus.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void save(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(), request.getEmail(), encodedPassword);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(new GetUserResponse(user.getName(), user.getCreatedAt(), user.getModifiedAt()));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public GetOneUserResponse getOne(Long userId) {
        User user = getUser(userId);
        return new GetOneUserResponse(user.getName(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt());
    }

    @Transactional
    public void update(Long userId, UpdateUserRequest request) {
        User user = getUser(userId);
        user.userUpdate(request.getName(), request.getEmail());
    }

    @Transactional
    public void delete(Long userId, HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        if (sessionValue == null) {
            throw new IllegalArgumentException("로그인이 필요한 작업입니다.");
        }
        boolean isMatch = userId.equals(sessionValue.getUserId());
        if (isMatch) {
            userRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("본인 유저정보만 삭제가 가능합니다.");
        }

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

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
    }

}
