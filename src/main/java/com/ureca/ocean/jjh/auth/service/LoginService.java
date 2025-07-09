package com.ureca.ocean.jjh.auth.service;

import com.ureca.ocean.jjh.auth.dto.LoginResultDto;

public interface LoginService {
    public LoginResultDto login(String username, String password);
}
