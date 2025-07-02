package com.example.authbackend.auth.service;

import com.example.authbackend.auth.dto.LoginResultDto;

import java.util.Map;

public interface LoginService {
    public LoginResultDto login(String username, String password);
}
