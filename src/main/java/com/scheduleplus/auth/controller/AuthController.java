package com.scheduleplus.auth.controller;

import com.scheduleplus.auth.service.AuthService;
import com.scheduleplus.auth.dto.CreateUserRequest;
import com.scheduleplus.auth.dto.LoginUserRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest request) {
        authService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody @Valid LoginUserRequest request, HttpSession session) {
        authService.login(request, session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
