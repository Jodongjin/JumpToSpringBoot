package com.example.sbb.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SiteUser { // 스프링 시큐리티에 이미 User 클래스가 있어서 SiteUser로 명명
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Builder
    public SiteUser(String username,
                    String password,
                    String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
