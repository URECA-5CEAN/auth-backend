package com.ureca.ocean.jjh.oauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.oauth.dto.KakaoLoginResultDto;
import com.ureca.ocean.jjh.oauth.service.KakaoAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Kakao Login API", description = "카카오 로그인 관련 API")
@RequestMapping("api/auth")
@RestController @RequiredArgsConstructor @Slf4j
public class OAuthController {
    private final KakaoAuthService kakaoAuthService;

    @Operation(summary = "카카오 콜백", description = "[개발완료] 카카오로 로그인 시도 -> 로그인(token=JWT)하거나 회원가입(token=KakaoAccessToken)")
    @GetMapping("kakao/callback")
    public void kakaoCallbackViaGet(
            @RequestParam("code") String code,
            HttpServletResponse response
    ) throws IOException
    {
        KakaoLoginResultDto kakaoLoginResultDto = kakaoAuthService.getKakaoLogin(code);

        // JSON 문자열로 변환
        String json = new ObjectMapper().writeValueAsString(BaseResponseDto.success(kakaoLoginResultDto));

        // HTML + JavaScript로 감싸서 부모 창으로 메시지 전송
        String html = "<html><body><script>" +
                "window.opener.postMessage(" + json + ", '*');" +
                "window.close();" +
                "</script></body></html>";

        response.setContentType("text/html");
        response.getWriter().write(html);

//        KakaoLoginResultDto kakaoLoginResultDto = kakaoAuthService.getKakaoLogin(code);
//        return ResponseEntity.ok(BaseResponseDto.success(kakaoLoginResultDto));
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
