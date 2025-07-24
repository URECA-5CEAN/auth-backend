package com.ureca.ocean.jjh.oauth.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.oauth.dto.KakaoLoginResultDto;
import com.ureca.ocean.jjh.oauth.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/auth")
@RestController @RequiredArgsConstructor @Slf4j
public class OAuthController {
    private final KakaoAuthService kakaoAuthService;

    @GetMapping("kakao/callback")
    public ResponseEntity<BaseResponseDto<?>> kakaoCallbackViaGet(
            @RequestParam("code") String code
    ) {
        KakaoLoginResultDto kakaoLoginResultDto = kakaoAuthService.getKakaoLogin(code);

        return ResponseEntity.ok(BaseResponseDto.success(kakaoLoginResultDto));
    }

    @PostMapping("kakao/signup")
    public ResponseEntity<BaseResponseDto<?>> kakaoSignup(
            @RequestParam("accessToken") String accessToken
    ) {
        KakaoLoginResultDto kakaoLoginResultDto = kakaoAuthService.kakaoSignupWithToken(accessToken);

        return ResponseEntity.ok(BaseResponseDto.success(kakaoLoginResultDto));
    }
}
