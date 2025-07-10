package com.ureca.ocean.jjh.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(20000, "INTERNAL_SERVER_ERROR",  "서버 내부 오류가 발생했습니다."),
    BAD_REQUEST(00401,"BAD_REQUEST","BODY가 비어있습니다.");
    private final int code;
    private final String name;
    private final String message;
}
