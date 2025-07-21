package com.ureca.ocean.jjh.oauth.service;

import com.ureca.ocean.jjh.oauth.dto.KakaoLoginResultDto;

public interface KakaoAuthService {
    KakaoLoginResultDto getKakaoLogin(String code);
}
