package com.ureca.ocean.jjh.oauth.dto;

import lombok.Data;

@Data
public class KakaoLoginResultDto {
    private String email;
    private String nickname;
    private String jwtToken;
}
