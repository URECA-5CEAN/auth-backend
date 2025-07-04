package com.example.authbackend.auth.service.impl;

import com.example.authbackend.auth.dto.LoginResultDto;
import com.example.authbackend.auth.service.LoginService;
//import com.example.authbackend.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
//    private final JwtUtil jwtUtil;
    @Override
    public LoginResultDto login(String email, String password) {
        LoginResultDto loginResultDto = new LoginResultDto();

        log.debug("login start");
//        try {
//            //Attempts to authenticate the passed Authentication object, returning afully populated Authentication object (including granted authorities)if successful.
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
//            String username = authentication.getName();
//            //get authoritiy는 collection인데 그 각각에서 getAuthority를 호출해서 list로 만들기.
//            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
//            log.debug("create token");
//            String token = jwtUtil.createToken(username, roles);
//            log.debug("create token :" + token);
//
//            loginResultDto.setResult("success");
//            loginResultDto.setToken(token);
//        }catch (Exception e) {
//            e.printStackTrace();
//            loginResultDto.setResult("fail");
//        }


        log.debug("login end");

        return loginResultDto;
    }
}
