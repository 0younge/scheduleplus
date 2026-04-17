package com.scheduleplus.user.usercontroller;

import com.scheduleplus.user.userdto.CreateUserRequest;
import com.scheduleplus.user.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
