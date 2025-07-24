package com.ureca.ocean.jjh.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoLoginResultDto {
    private String result;
    private String name;
    private String nickname;
    private String email;
    private String gender;
    private String token;

    public KakaoLoginResultDto(
            String result, String name, String nickname, String email, String gender, String token
    ) {
        this.result = result;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.token = token;
    }
}
