package com.ureca.ocean.jjh.auth.controller;

import com.ureca.ocean.jjh.auth.dto.LoginResultDto;
import com.ureca.ocean.jjh.auth.service.impl.LoginServiceImpl;
import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginServiceImpl loginService;
    @PostMapping("/health")
    public String health(){
        log.info("auth health checking...");
        return "auth health check fine";
    }


    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto<?>>  login(@RequestBody(required = false) Map<String,String> loginRequest) {
        if (loginRequest == null || loginRequest.isEmpty()) {
            log.info("request 정보가 없습니다.");
            return ResponseEntity.badRequest().body(BaseResponseDto.fail(ErrorCode.INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.ok(BaseResponseDto.success(loginService.login(loginRequest.get("email"), loginRequest.get("password"))));
    }


}
