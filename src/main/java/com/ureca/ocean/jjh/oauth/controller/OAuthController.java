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
            @RequestParam String code,
            HttpServletResponse response
    ) throws IOException {
        log.info("인가 코드 수신: {}", code);

        // KakaoTokenResponseDto, KakaoUserResponseDto 등은 kakaoAuthService 내부에서 대체
        KakaoLoginResultDto kakaoLoginResultDto = kakaoAuthService.getKakaoLogin(code);
        // 로그인 성공 시 JWT, 회원가입 필요 시 Kakao accessToken 반환 구조

        // 응답 JSON 구성
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("result", kakaoLoginResultDto.getResult());
        result.put("name", kakaoLoginResultDto.getName());
        result.put("nickname", kakaoLoginResultDto.getNickname());
        result.put("email", kakaoLoginResultDto.getEmail());
        result.put("gender", kakaoLoginResultDto.getGender());
        result.put("token", kakaoLoginResultDto.getToken());

        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        objectMapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.ESCAPE_NON_ASCII, false); // 한글 깨짐 방지
        String json = objectMapper.writeValueAsString(result);

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(
                "<!DOCTYPE html>\n" +
                "<html><head><meta charset='UTF-8'><title>Kakao Login</title></head><body>" +
                "<script>\n" +
                "  const result = " + json + ";\n" +
                "  if (window.opener) {\n" +
                "    window.opener.postMessage(result, '*');\n" +
                "    window.close();\n" +
                "  } else {\n" +
                "    document.body.innerText = '로그인 완료: 콘솔을 확인하세요.';\n" +
                "    console.log(result);\n" +
                "  }\n" +
                "</script></body></html>"
        );
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
