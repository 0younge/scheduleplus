package com.scheduleplus.user.entity;

import com.scheduleplus.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void userUpdate(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void authorVerification(String sessionValueName) {
        if (!name.equals(sessionValueName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자가 일치하지 않습니다.");
        }
    }



}
