package com.ureca.ocean.jjh.oauth.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.oauth.dto.KakaoLoginResultDto;
import com.ureca.ocean.jjh.oauth.service.KakaoAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Kakao Login API", description = "카카오 로그인 관련 API")
@RequestMapping("api/auth")
@RestController @RequiredArgsConstructor @Slf4j
public class OAuthController {
    private final KakaoAuthService kakaoAuthService;

    @Operation(summary = "카카오 콜백", description = "[개발완료] 카카오로 로그인 시도 -> 로그인(token=JWT)하거나 회원가입(token=KakaoAccessToken)")
    @GetMapping("kakao/callback")
    public ResponseEntity<BaseResponseDto<?>> kakaoCallbackViaGet(
            @RequestParam("code") String code
    ) {
        KakaoLoginResultDto kakaoLoginResultDto = kakaoAuthService.getKakaoLogin(code);

        return ResponseEntity.ok(BaseResponseDto.success(kakaoLoginResultDto));
    }

    @Operation(summary = "카카오로 회원가입", description = "[개발완료] accessToken에 콜백에서 받았던 token을 넣으세요")
    @PostMapping("kakao/signup")
    public ResponseEntity<BaseResponseDto<?>> kakaoSignup(
            @RequestParam("accessToken") String accessToken
    ) {
        KakaoLoginResultDto kakaoLoginResultDto = kakaoAuthService.kakaoSignupWithToken(accessToken);

        return ResponseEntity.ok(BaseResponseDto.success(kakaoLoginResultDto));
    }
}
