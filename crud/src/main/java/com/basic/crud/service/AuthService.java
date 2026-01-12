package com.basic.crud.service;

import com.basic.crud.dto.MemberLoginRequestDto;
import com.basic.crud.dto.MemberLoginResponseDto;
import com.basic.crud.entity.Member;
import com.basic.crud.enums.Role;
import com.basic.crud.repository.MemberRepository;
import com.basic.crud.security.dto.UserData;
import com.basic.crud.security.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final JwtService jwtService;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // 로그인 처리
    @Transactional
    public MemberLoginResponseDto login(MemberLoginRequestDto requestDto) {

        // 데이터 준비
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 받은 email로 회원 조회
        Member foundMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        // 비밀번호 검증 - 받아온 비밀번호와 기존 비밀번호가 일치하는지 확인
        String encodedPassword = foundMember.getPassword();
        boolean match = passwordEncoder.matches(password, encodedPassword);
        if (!match) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 토큰 만들기
        Long memberId = foundMember.getId();
        String role = foundMember.getRole().name();

        String token = jwtService.createToken(new UserData(memberId, role));

        // DTO 생성
        MemberLoginResponseDto responseDto = new MemberLoginResponseDto(token);
        return responseDto;
    }
}
