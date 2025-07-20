package com.ureca.ocean.jjh.oauth.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.oauth.dto.KakaoLoginResultDto;
import com.ureca.ocean.jjh.oauth.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("api/auth")
@RestController @RequiredArgsConstructor @Slf4j
public class OAuthController {
    private final KakaoAuthService kakaoAuthService;

    @GetMapping("kakao/callback")
    public ResponseEntity<BaseResponseDto<?>> kakaoCallback(
            @RequestParam("code") String code
    ) {
        log.info("kakao auth callback code={}", code);
        KakaoLoginResultDto kakaoLoginResultDto = kakaoAuthService.getKakaoLogin(code);
        return ResponseEntity.ok(BaseResponseDto.success(kakaoLoginResultDto));
    }
}
