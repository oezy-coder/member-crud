package com.basic.crud.dto;

public class MemberDetailResponseDto {

    private Long id;
    private String name;

    public MemberDetailResponseDto(Long memberId, String name) {
        this.id = memberId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
