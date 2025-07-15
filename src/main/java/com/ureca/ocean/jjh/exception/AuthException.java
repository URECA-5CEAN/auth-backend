package com.ureca.ocean.jjh.exception;

import com.ureca.ocean.jjh.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthException extends RuntimeException{
    private final ErrorCode errorCode;
}
