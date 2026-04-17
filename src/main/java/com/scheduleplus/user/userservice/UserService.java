package com.scheduleplus.user.userservice;

import com.scheduleplus.user.userdto.CreateUserRequest;
import com.scheduleplus.user.userdto.GetOneUserResponse;
import com.scheduleplus.user.userdto.GetUserResponse;
import com.scheduleplus.user.userdto.UpdateUserRequest;
import com.scheduleplus.user.userentity.User;
import com.scheduleplus.user.userrepository.UserRepository;
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
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
        return new GetOneUserResponse(user.getName(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt());
    }

    @Transactional
    public void update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
        user.userUpdate(request.getName(), request.getEmail());
    }
}
