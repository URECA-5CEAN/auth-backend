package com.ureca.ocean.jjh.oauth.dto;

import lombok.Data;

@Data
public class KakaoUserInfoDto {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;

    @Data
    public static class KakaoAccount {
        private String email;
        private Profile profile;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
    }

    @Data
    public static class Profile {
        private String nickname;
        private String profile_image_url;
        private String thumbnail_image_url;
        private Boolean is_default_image;
    }
}