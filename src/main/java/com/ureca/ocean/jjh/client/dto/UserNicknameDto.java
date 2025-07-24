package com.ureca.ocean.jjh.client.dto;

import lombok.Data;

import java.util.UUID;
@Data

public class UserNicknameDto {
    private UUID id;
    private String name;
    private String nickname;
    private String email;
    private String password;
}