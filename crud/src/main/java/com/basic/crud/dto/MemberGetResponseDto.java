package com.basic.crud.dto;

public class MemberGetResponseDto {

    private Long id;
    private String name;
    private int count;

    public MemberGetResponseDto(Long memberId, String name, int count) {
        this.id = memberId;
        this.name = name;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
