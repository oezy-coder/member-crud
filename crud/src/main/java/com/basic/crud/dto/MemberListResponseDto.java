package com.basic.crud.dto;

import java.util.List;

public class MemberListResponseDto {

    private Integer count;
    private List<MemberGetAllResponseDto> memberList;

    public MemberListResponseDto(Integer count, List<MemberGetAllResponseDto> memberList) {
        this.count = count;
        this.memberList = memberList;
    }

    public Integer getCount() {
        return count;
    }

    public List<MemberGetAllResponseDto> getMemberList() {
        return memberList;
    }

    // 내부 클래스(중첩 클래스)
    public static class MemberGetAllResponseDto {

        private Long id;
        private String name;

        public MemberGetAllResponseDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
