package com.basic.crud.dto;

public class MemberLoginResponseDto {

    private final String token;

    public MemberLoginResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
