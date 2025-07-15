package com.ureca.ocean.jjh.auth.service.impl;

import com.ureca.ocean.jjh.auth.dto.LoginResultDto;
import com.ureca.ocean.jjh.auth.service.LoginService;

import com.ureca.ocean.jjh.common.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.AuthException;
import com.ureca.ocean.jjh.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;



@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public LoginResultDto login(String email, String password) {
        LoginResultDto loginResultDto = new LoginResultDto();

        log.debug("login start");
        try {
            log.info("email : " + email + "password : " + password);

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

            String token = jwtUtil.createToken(email);
            log.debug("created token :" + token);

            loginResultDto.setResult("login success");
            loginResultDto.setToken(token);
        }catch (Exception e) {
            throw new AuthException(ErrorCode.LOGIN_FAIL);
        }


        log.debug("login end");

        return loginResultDto;
    }
}
