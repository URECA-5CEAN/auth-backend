package com.example.authbackend.auth.controller;

import com.example.authbackend.auth.dto.LoginResultDto;
import com.example.authbackend.auth.service.impl.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginServiceImpl loginService;

//    @PostMapping("/login")
//    public LoginResultDto login(@RequestBody Map<String,String> loginData) { //이전에는 session을 받아서 session에 담았었는데 이제 필요 x, logout도 필요 x (expired date가 되면 logout되는 개념으로 만들었기 때문ㅇ)
//        return loginService.login(loginData.get("username"), loginData.get("password"));
//    }

    @PostMapping("/login")
    public Long lgoin(){
        log.info("test");
        //jwt return
        return 1L;
    }
}
