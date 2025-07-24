package com.ureca.ocean.jjh.oauth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.ocean.jjh.client.UserClient;
import com.ureca.ocean.jjh.client.dto.UserDto;
import com.ureca.ocean.jjh.common.entity.enums.Gender;
import com.ureca.ocean.jjh.common.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.AuthException;
import com.ureca.ocean.jjh.oauth.dto.KakaoLoginResultDto;
import com.ureca.ocean.jjh.oauth.dto.KakaoTokenResponseDto;
import com.ureca.ocean.jjh.oauth.dto.KakaoUserInfoDto;
import com.ureca.ocean.jjh.oauth.dto.SignUpRequestDto;
import com.ureca.ocean.jjh.oauth.service.KakaoAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class KakaoAuthServiceImpl implements KakaoAuthService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    UserClient userClient;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${myapp.jwt.secret}")
    private String jwtSecret;


    @Override
    public KakaoLoginResultDto getKakaoLogin(String code) {
        String accessToken = getAccessToken(code);
        KakaoUserInfoDto userInfo = getUserInfo(accessToken);
        KakaoLoginResultDto kakaoLoginResultDto;

        // 이메일 중복 여부 확인
        try {
            UserDto existingUser = userClient.getUserByEmail(userInfo.getKakaoAccount().getEmail());

            // 일반 로그인 계정이면 카카오 로그인 차단
            if (!existingUser.getPassword().startsWith("{kakao}")) {
                throw new AuthException(ErrorCode.NORMAL_USER_ALREADY_EXIST);
            }
        } catch (Exception ex) {
            // 사용자 없음 → 회원가입 분기: 회원정보 반환
            kakaoLoginResultDto = new KakaoLoginResultDto(
                    "signup required",
                    userInfo.getKakaoAccount().getName(),
                    userInfo.getKakaoAccount().getProfile().getNickName(),
                    userInfo.getKakaoAccount().getEmail(),
                    userInfo.getKakaoAccount().getGender(),
                    // token : accessToken
                    accessToken
            );
            return kakaoLoginResultDto;
        }

        // 카카오 로그인 성공
        String jwt = createJwtToken(userInfo.getKakaoAccount().getEmail());

        kakaoLoginResultDto = new KakaoLoginResultDto(
                "login success",
                userInfo.getKakaoAccount().getName(),
                userInfo.getKakaoAccount().getProfile().getNickName(),
                userInfo.getKakaoAccount().getEmail(),
                userInfo.getKakaoAccount().getGender(),
                jwt
        );

        return kakaoLoginResultDto;
    }

    // Sign up
    @Override
    public KakaoLoginResultDto kakaoSignupWithToken(String accessToken) {
        KakaoUserInfoDto userInfo = getUserInfo(accessToken);

        // 회원가입 DTO 구성
        SignUpRequestDto signupRequestDto = SignUpRequestDto.builder()
                .email(userInfo.getKakaoAccount().getEmail())
                .name(userInfo.getKakaoAccount().getName())
                .nickname(userInfo.getKakaoAccount().getProfile().getNickName())
                .gender(getGenderSafely(userInfo.getKakaoAccount().getGender()))
                .password("{kakao}" + UUID.randomUUID())  // 패스워드 대체 마커
                .build();

        userClient.signup(signupRequestDto);

        // 회원가입 후 JWT 발급
        String jwt = createJwtToken(userInfo.getKakaoAccount().getEmail());

        return KakaoLoginResultDto.builder()
                .name(userInfo.getKakaoAccount().getName())
                .nickname(userInfo.getKakaoAccount().getProfile().getNickName())
                .email(userInfo.getKakaoAccount().getEmail())
                .gender(userInfo.getKakaoAccount().getGender())
                .token(jwt)
                .build();
    }

    private String getAccessToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // exception 처리
        try {
            ResponseEntity<String> response = new RestTemplate().exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
            );

            System.out.println("카카오 accessToken 응답: " + response.getBody());

            KakaoTokenResponseDto tokenResponse = objectMapper.readValue(response.getBody(), KakaoTokenResponseDto.class);
            if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
                throw new AuthException(ErrorCode.KAKAO_RESPONSE_FAIL);
            }
            return tokenResponse.getAccessToken();
        } catch (Exception e) {
            System.out.println("카카오 accessToken 예외 발생: " + e.getMessage());
            e.printStackTrace();
            throw new AuthException(ErrorCode.KAKAO_RESPONSE_FAIL);
        }
    }

    public KakaoUserInfoDto getUserInfo(String accessToken) {
        String requestUri = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(KakaoAuthServiceImpl.class);

        // exception 처리
        log.debug("getUserInfo start");
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<KakaoUserInfoDto> response = restTemplate.exchange(
                requestUri,
                HttpMethod.GET,
                entity,
                KakaoUserInfoDto.class
            );

            KakaoUserInfoDto userInfo = response.getBody();

            if (userInfo == null) {
                log.error("Kakao userInfo is null");
                throw new AuthException(ErrorCode.KAKAO_LOGIN_FAIL);
            }

            if (userInfo.getKakaoAccount() == null) {
                log.warn("KakaoAccount is null in response");
            } else {
                log.debug("KakaoAccount info: email={}, gender={}, name={}, phone={}",
                        userInfo.getKakaoAccount().getEmail(),
                        userInfo.getKakaoAccount().getGender(),
                        userInfo.getKakaoAccount().getName(),
                        userInfo.getKakaoAccount().getPhoneNumber());

                if (userInfo.getKakaoAccount().getProfile() != null) {
                    log.debug("Profile nickname: {}", userInfo.getKakaoAccount().getProfile().getNickName());
                } else {
                    log.warn("Profile is null in KakaoAccount");
                }
            }

            return userInfo;

        } catch (org.springframework.web.client.RestClientException e) {
            throw new AuthException(ErrorCode.KAKAO_LOGIN_FAIL);
        } finally {
            log.debug("getUserInfo end");
        }
    }

    private String createJwtToken(String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    private Gender getGenderSafely(String genderStr) {
        try {
            return Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new AuthException(ErrorCode.INVALID_GENDER);
        }
    }
}
