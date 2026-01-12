package com.basic.crud.security.dto;

public class AuthInfo {

    private Long memberId;
    private String email;
    private String role;

    public AuthInfo(Long memberId, String email, String role) {
        this.memberId = memberId;
        this.email = email;
        this.role = role;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // 로그인한 회원의 ID와 접근하려는 ID가 일치하는지 확인
    public boolean isOwnerOf(Long memberId) {
        return this.memberId != null && this.memberId.equals(memberId);
    }

    // 관리자만 접근 가능
    public boolean isAdmin() {
        return "ADMIN".equals(this.role);
    }
}
