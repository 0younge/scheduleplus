package com.scheduleplus.user.userservice;

import com.scheduleplus.user.userdto.CreateUserRequest;
import com.scheduleplus.user.userentity.User;
import com.scheduleplus.user.userrepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(CreateUserRequest request) {
        User user = new User(request.getName(), request.getEmail(), request.getPassword());
        userRepository.save(user);
    }
}
