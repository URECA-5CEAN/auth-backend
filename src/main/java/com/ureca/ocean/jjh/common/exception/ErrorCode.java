package com.ureca.ocean.jjh.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //auth_backend : 10000
    LOGIN_FAIL(10000,"LOGIN_FAIL","LOGIN에 실패했습니다."),
    KAKAO_LOGIN_FAIL(10001,"KAKAO_LOGIN_FAIL","KAKAO LOGIN에 실패했습니다."),
    KAKAO_RESPONSE_FAIL(10002,"KAKAO_RESPONSE_FAIL","KAKAO RESPONSE에 실패했습니다."),

    //user_backend : 20000
    NOT_FOUND_USER(20001,"NOT_FOUND_USER","해당 사용자가 없습니다."),
    USER_ALREADY_EXIST(20002,"USER_ALREADY_EXIST","해당 사용자가 이미 있습니다.");

    //map_backend : 30000

    private final int code;
    private final String name;
    private final String message;
}
