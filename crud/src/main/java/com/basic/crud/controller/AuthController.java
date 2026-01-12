package com.basic.crud.controller;

import com.basic.crud.dto.ApiResponse;
import com.basic.crud.dto.MemberLoginRequestDto;
import com.basic.crud.dto.MemberLoginResponseDto;
import com.basic.crud.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> loginApi(@RequestBody MemberLoginRequestDto requestDto) {

       MemberLoginResponseDto responseDto = authService.login(requestDto);

       ApiResponse<MemberLoginResponseDto> apiResponse = new ApiResponse<>("success", HttpStatus.OK.value(), responseDto);
       ResponseEntity<ApiResponse<MemberLoginResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
       return response;
    }
}
