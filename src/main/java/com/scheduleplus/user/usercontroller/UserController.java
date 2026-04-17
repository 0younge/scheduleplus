package com.scheduleplus.user.usercontroller;

import com.scheduleplus.user.userdto.*;
import com.scheduleplus.user.userservice.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOne(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        userService.update(userId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody LoginUserRequest request, HttpSession session) {
        userService.login(request, session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
