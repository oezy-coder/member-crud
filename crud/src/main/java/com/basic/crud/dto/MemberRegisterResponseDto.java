package com.basic.crud.dto;;

public class MemberRegisterResponseDto {

    private Long id;

    public MemberRegisterResponseDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
