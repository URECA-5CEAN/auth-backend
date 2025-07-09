package com.ureca.ocean.jjh.user.entity.enums;


public enum Membership {
    VVIP,
    VIP,
    우수,
    NORMAL_방문자;

    @Override
    public String toString() {
        return name().replace("_", "(").replaceFirst("\\(", "(").replaceAll("\\(", "").replaceAll("\\)", "");
    }
}