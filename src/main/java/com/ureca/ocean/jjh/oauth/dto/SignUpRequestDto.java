package com.ureca.ocean.jjh.oauth.dto;

import com.ureca.ocean.jjh.common.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String name;         // nullable = false
    private String nickname;     // nullable = false
    private String email;        // nullable = false
    private String password;     // nullable = false
    private Gender gender;       // nullable = false
}