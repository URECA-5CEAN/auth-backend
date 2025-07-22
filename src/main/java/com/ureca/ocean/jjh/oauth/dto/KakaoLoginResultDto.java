package com.ureca.ocean.jjh.oauth.dto;

import lombok.Data;

@Data
public class KakaoLoginResultDto {
    private String name;
    private String nickname;
    private String email;
    private String gender;
    private String jwtToken;

    public KakaoLoginResultDto(
            String email, String nickname, String jwtToken, String name, String gender
    ) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.jwtToken = jwtToken;
    }
}
