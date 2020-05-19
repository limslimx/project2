package com.lim.poly.project2.web.dto;


import com.lim.poly.project2.domain.Member;
import com.lim.poly.project2.domain.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class MemberSignUpForm {

    @NotBlank(message = "올바른 형식의 아이디여야 합니다")
    private String uId;

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "올바른 형식의 비밀번호여야 합니다")
    private String password;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "올바른 형식의 비밀번호여야 합니다")
    private String passwordCheck;

    private Role role;

    public Member toEntity() {
        return Member.builder()
                .uId(uId)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}