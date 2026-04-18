package com.scheduleplus.user.service;

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

    @Transactional
    public void save(CreateUserRequest request) {
        User user = new User(request.getName(), request.getEmail(), request.getPassword());
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
    public void delete(Long userId) {
        getUser(userId);
        userRepository.deleteById(userId);
    }

    @Transactional
    public void login(LoginUserRequest request, HttpSession session) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        SessionValue sessionValue = new SessionValue(user.getUserId(), user.getName());
        session.setAttribute("sessionId", sessionValue);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
    }

}
