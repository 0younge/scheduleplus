package com.scheduleplus.common;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public abstract class BaseController {

    /**
     * 세션 여부 확인 메서드
     * @param session 검증을 위한 세션
     * @return null을 확인한 세션정보값 반환
     */
    public SessionValue authSession(HttpSession session) {
        SessionValue sessionValue = (SessionValue) session.getAttribute("sessionId");
        if (sessionValue == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 작업입니다.");
        }

        return sessionValue;
    }
}
