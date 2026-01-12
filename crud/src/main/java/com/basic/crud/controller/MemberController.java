package com.basic.crud.controller;

import com.basic.crud.dto.*;
import com.basic.crud.security.dto.AuthInfo;
import com.basic.crud.security.jwt.JwtService;
import com.basic.crud.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    // 속성
    private final MemberService memberService;
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final JwtService jwtService;

    // 생성자
    public MemberController(MemberService memberService, JwtService jwtService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    // 회원가입 API
    @PostMapping
    public ResponseEntity<ApiResponse<MemberRegisterResponseDto>> memberRegisterApi(@RequestBody MemberRegisterRequestDto requestDto) {

//        log.info("email: {}", requestDto.getEmail());
//        log.info("password: {}", requestDto.getPassword());

        // 핵심 비즈니스 로직
        MemberRegisterResponseDto responseDto = memberService.registerMember(requestDto);

        // 응답 반환
        ApiResponse<MemberRegisterResponseDto> apiResponse = new ApiResponse<>("Created", 201, responseDto);
        ResponseEntity<ApiResponse<MemberRegisterResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        return response;
    }

    // 회원 단건 상세 조회 API
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberDetailResponseDto>> getMemberDetailApi(
            @PathVariable("memberId") Long memberId,
            @RequestHeader("Authorization") String authorization) {
//        log.info("controller - memberId: {}", memberId);

        // JWT 검증 & 인증된 유저 정보 조회
        AuthInfo authInfo = jwtService.verifyToken(authorization);
        MemberDetailResponseDto responseDto = memberService.getMemberDetail(memberId, authInfo);

        ApiResponse<MemberDetailResponseDto> apiResponse = new ApiResponse<>("OK", 200, responseDto);
        ResponseEntity<ApiResponse<MemberDetailResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        return response;
    }

    // 회원 다건 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<MemberListResponseDto>> getMemberListApi(
            @RequestHeader("Authorization") String authorization) {
//        log.info("controller - memberList");

        AuthInfo authInfo = jwtService.verifyToken(authorization);
        MemberListResponseDto responseDto = memberService.getMemberList(authInfo);

        ApiResponse<MemberListResponseDto> apiResponse = new ApiResponse<>("OK", 200, responseDto);
        ResponseEntity<ApiResponse<MemberListResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        return response;
    }

    // 회원 수정 API
    @PatchMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberUpdateResponseDto>> updateMemberApi(
            @PathVariable("memberId") Long memberId,
            @RequestHeader("Authorization") String authorization,
            @RequestBody MemberUpdateRequestDto requestDto) {
//        log.info("controller - memberId: {}", memberId);

        AuthInfo authInfo = jwtService.verifyToken(authorization);
        MemberUpdateResponseDto responseDto = memberService.updateMember(memberId, authInfo, requestDto);

        ApiResponse<MemberUpdateResponseDto> apiResponse = new ApiResponse<>("OK", HttpStatus.OK.value(), responseDto);
        ResponseEntity<ApiResponse<MemberUpdateResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        return response;
    }

    // 회원 삭제 API
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberDeleteResponseDto>> deleteMemberApi(
            @PathVariable("memberId") Long memberId,
            @RequestHeader("Authorization") String authorization) {

        AuthInfo authInfo = jwtService.verifyToken(authorization);
        MemberDeleteResponseDto responseDto = memberService.deleteMember(memberId, authInfo);

        ApiResponse<MemberDeleteResponseDto> apiResponse = new ApiResponse<>("Deleted", HttpStatus.OK.value(), responseDto);
        ResponseEntity<ApiResponse<MemberDeleteResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        return response;
    }
}
