package com.ureca.ocean.jjh.oauth.service.impl;

import com.ureca.ocean.jjh.oauth.dto.KakaoLoginResultDto;
import com.ureca.ocean.jjh.oauth.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthServiceImpl implements KakaoAuthService {
    @Override
    public KakaoLoginResultDto getKakaoLogin(String code) {
        return null;
    }
}
