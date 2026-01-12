package com.basic.crud.security.dto;

public class UserData {

    private Long memberId;
    private String role;

    public UserData(Long memberId, String role) {
        this.memberId = memberId;
        this.role = role;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getRole() {
        return role;
    }
}
