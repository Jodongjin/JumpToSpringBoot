package com.example.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String passwordCheck;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email // Email Format 확인
    private String email;

    @Builder
    public UserCreateForm(String username,
                          String password,
                          String passwordCheck,
                          String email) {
        this.username = username;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.email = email;
    }
}
