package com.basic.crud.dto;

public class ApiResponse<T> {

    private String message;
    private Integer status;
    private T data;

    public ApiResponse(String message, Integer status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

}
