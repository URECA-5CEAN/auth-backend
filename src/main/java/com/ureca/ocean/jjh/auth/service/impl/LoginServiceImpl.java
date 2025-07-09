package com.ureca.ocean.jjh.auth.service.impl;

import com.ureca.ocean.jjh.auth.dto.LoginResultDto;
import com.ureca.ocean.jjh.auth.service.LoginService;

import com.ureca.ocean.jjh.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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
            //Attempts to authenticate the passed Authentication object, returning afully populated Authentication object (including granted authorities)if successful.
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            String username = authentication.getName();
            log.debug("create token");
            String token = jwtUtil.createToken(username);
            log.debug("create token :" + token);

            loginResultDto.setResult("success");
            loginResultDto.setToken(token);
        }catch (Exception e) {
            e.printStackTrace();
            loginResultDto.setResult("fail");
        }


        log.debug("login end");

        return loginResultDto;
    }
}
