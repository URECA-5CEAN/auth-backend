package com.ureca.ocean.jjh.client;

import com.ureca.ocean.jjh.client.dto.UserNicknameDto;
import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.common.constant.DomainConstant;
import com.ureca.ocean.jjh.client.dto.UserDto;
import com.ureca.ocean.jjh.oauth.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserClient {
    private final RestTemplate restTemplate;

    public UserDto getUserByEmail(String email) {
        String url = DomainConstant.USER_URL + "api/user?email="+ email ;
        System.out.println("url : "+ url);
        ResponseEntity<BaseResponseDto<UserDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BaseResponseDto<UserDto>>() {}
        );
        UserDto userDto = response.getBody().getData();
        log.info("userDto 받은 거 : " + userDto.getPassword() );
        return userDto;
    }

    // 닉네임을 받아오기 위한 눈물나는 노력
    public UserNicknameDto getUserAndNicknameByEmail(String email) {
        String url = DomainConstant.USER_URL + "api/user?email="+ email ;
        System.out.println("url : "+ url);
        ResponseEntity<BaseResponseDto<UserNicknameDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BaseResponseDto<UserNicknameDto>>() {}
        );
        UserNicknameDto userNicknameDto = response.getBody().getData();
        return userNicknameDto;
    }

    public UserDto signup(SignUpRequestDto signUpRequestDto) {
        String url = DomainConstant.USER_URL + "api/user/signup";

        try {
            ResponseEntity<BaseResponseDto<UserDto>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(signUpRequestDto),
                new ParameterizedTypeReference<BaseResponseDto<UserDto>>() {}
            );

            return response.getBody().getData();
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean existsByNickname(String nickname) {
        String url = DomainConstant.USER_URL + "api/user/isDupNickname?nickname=" + nickname;
        try {
            ResponseEntity<BaseResponseDto<Boolean>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<BaseResponseDto<Boolean>>() {}
            );
            return Boolean.TRUE.equals(response.getBody().getData());
        } catch (Exception e) {
            return false; // fallback: 중복 아님 처리
        }
    }
}