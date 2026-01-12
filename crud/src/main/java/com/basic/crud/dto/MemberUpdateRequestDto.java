package com.basic.crud.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberUpdateRequestDto {

    private String name;

    @JsonCreator
    public MemberUpdateRequestDto(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
