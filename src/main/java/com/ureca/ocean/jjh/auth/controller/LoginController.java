package com.ureca.ocean.jjh.auth.controller;

import com.ureca.ocean.jjh.auth.dto.LoginResultDto;
import com.ureca.ocean.jjh.auth.service.impl.LoginServiceImpl;
import com.ureca.ocean.jjh.common.BaseResponseDto;
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
    public ResponseEntity<BaseResponseDto<LoginResultDto>>  login(@RequestBody Map<String,String> loginRequest) { //이전에는 session을 받아서 session에 담았었는데 이제 필요 x, logout도 필요 x (expired date가 되면 logout되는 개념으로 만들었기 때문ㅇ)
        return ResponseEntity.ok(BaseResponseDto.success(loginService.login(loginRequest.get("email"), loginRequest.get("password"))));
    }


}
