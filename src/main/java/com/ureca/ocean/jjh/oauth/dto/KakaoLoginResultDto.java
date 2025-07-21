package com.ureca.ocean.jjh.oauth.dto;

import lombok.Data;

@Data
public class KakaoLoginResultDto {
    private String email;
    private String nickname;
    private String jwtToken;

    public KakaoLoginResultDto(String email, String nickname, String jwtToken) {
        this.email = email;
        this.nickname = nickname;
        this.jwtToken = jwtToken;
    }
}
