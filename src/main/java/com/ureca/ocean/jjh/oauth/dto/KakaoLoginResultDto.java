package com.ureca.ocean.jjh.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoLoginResultDto {
    private String name;
    private String nickname;
    private String email;
    private String gender;
    private String jwtToken;

    public KakaoLoginResultDto(
            String name, String nickname, String email, String gender, String jwtToken
    ) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.jwtToken = jwtToken;
    }
}
