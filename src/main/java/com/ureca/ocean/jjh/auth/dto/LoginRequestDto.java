package com.ureca.ocean.jjh.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    @Schema(description = "로그인 이메일", example = "test@example.com")
    private String email;

    @Schema(description = "비밀번호", example = "mypassword123")
    private String password;
}
