package com.scheduleplus.auth.controller;

import com.scheduleplus.auth.service.AuthService;
import com.scheduleplus.auth.dto.CreateUserRequest;
import com.scheduleplus.auth.dto.LoginUserRequest;
import com.scheduleplus.common.BaseController;
import com.scheduleplus.common.SessionConst;
import com.scheduleplus.common.UserAuthInfo;
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
public class AuthController extends BaseController {

    private final AuthService authService;

    /**
     * 회원가입
     * @param request 회원가입에 필요한 유저 정보(이름, 이메일, 비밀번호)
     * @return 상태코드
     */
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest request) {
        authService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그인
     * @param request 로그인에 필요한 정보(이메일, 비밀번호)
     * @param session 검증에 필요한 세션
     * @return 상태코드
     */
    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody @Valid LoginUserRequest request, HttpSession session) {
        UserAuthInfo userAuthInfo = authService.login(request);
        session.setAttribute(SessionConst.USER_ID, userAuthInfo.getUserId());
        session.setAttribute(SessionConst.USER_NAME, userAuthInfo.getName());

        return ResponseEntity.ok().build();
    }

    /**
     * 로그아웃
     * @param session 해제할 세션
     * @return 상태코드
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpSession session) {
        authSession(session);
        session.invalidate();

        return ResponseEntity.ok().build();
    }

}
