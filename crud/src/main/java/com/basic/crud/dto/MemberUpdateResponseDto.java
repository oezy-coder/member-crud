package com.basic.crud.dto;

public class MemberUpdateResponseDto {

    private Long id;

    public MemberUpdateResponseDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
