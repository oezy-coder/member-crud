package com.basic.crud.dto;

public class MemberDeleteResponseDto {

    private Long id;

    public MemberDeleteResponseDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
