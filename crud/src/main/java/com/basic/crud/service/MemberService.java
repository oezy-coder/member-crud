package com.basic.crud.service;

import com.basic.crud.dto.*;
import com.basic.crud.entity.Member;
import com.basic.crud.repository.MemberRepository;
import com.basic.crud.security.dto.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직
    @Transactional
    public MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto) {

        // 데이터 준비
        String name = requestDto.getName();
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 이메일 중복 여부 확인
        Boolean exist = memberRepository.existsByEmail(email);
        if (exist) {
            throw new RuntimeException("회원이 이미 존재합니다.");
        }

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(password);

        // 회원 엔티티 생성
        Member newMember = new Member(name, email, encodePassword);

        // 저장 - 레포지토리 활용
        Member savedMember = memberRepository.save(newMember);
        Long savedMemberId = savedMember.getId();

        // DTO 생성
        MemberRegisterResponseDto responseDto = new MemberRegisterResponseDto(savedMemberId);
        return responseDto;
    }

    // 회원 상세 정보 조회 로직
    @Transactional(readOnly = true)
    public MemberDetailResponseDto getMemberDetail(Long memberId, AuthInfo authInfo) {

        // 검증
        if (!authInfo.isOwnerOf(memberId)) {
            throw new IllegalArgumentException("접근 불가입니다.");
        }

        // 회원 상세 조회
       Member foundMember = memberRepository.findByIdAndIsDeletedFalse(memberId)
               .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

       // DTO를 만들기 위한 데이터 준비
        Long foundMemberId = foundMember.getId();
        String foundMemberName = foundMember.getName();

        // DTO 만들기
        MemberDetailResponseDto responseDto = new MemberDetailResponseDto(foundMemberId, foundMemberName);
        return responseDto;

//        log.info("service - memberId: {}", memberId);
    }

    // 회원 다건 조회 로직
    @Transactional(readOnly = true)
    public MemberListResponseDto getMemberList(AuthInfo authInfo) {
        log.info("service - memberList");

        // 검증
        if (!authInfo.isAdmin()) {
            throw new IllegalArgumentException("접근 불가입니다.");
        }

        // 데이터 조회
        List<Member> memberList = memberRepository.findByIsDeletedFalse();
        int count = memberList.size();

        // 내부 dto 만들기
        List<MemberListResponseDto.MemberGetAllResponseDto> memberGetAllResponseDtoList = new ArrayList<>();

        for (Member member : memberList) {

            Long foundMemberId = member.getId();
            String foundMemberName = member.getName();

            // 내부 dto를 new 키워드를 사용해서 생성자 호출
            MemberListResponseDto.MemberGetAllResponseDto responseDto = new MemberListResponseDto.MemberGetAllResponseDto(foundMemberId, foundMemberName);

            memberGetAllResponseDtoList.add(responseDto);
            log.info("memberId: {}", member.getId());
        }

        // 외부 dto 만들기
        MemberListResponseDto memberListResponseDto = new MemberListResponseDto(count, memberGetAllResponseDtoList);
        return memberListResponseDto;
    }

    // 회원 수정 로직
    @Transactional
    public MemberUpdateResponseDto updateMember(Long memberId, AuthInfo authInfo, MemberUpdateRequestDto requestDto) {
        log.info("service - name: {}", requestDto.getName());
        log.info("memberId: {}", memberId);

        // 검증
        if (!authInfo.isOwnerOf(memberId)) {
            throw new IllegalArgumentException("접근 불가입니다.");
        }

        // 회원 조회
        Member foundMember = memberRepository.findByIdAndIsDeletedFalse(memberId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
        
        // 회원 수정
        Member updatedMember = foundMember.updateMemberName(requestDto.getName());

        // DTO 만들기
        MemberUpdateResponseDto memberResponseDto = new MemberUpdateResponseDto(updatedMember.getId());
        return memberResponseDto;
    }

    // 회원 삭제 로직
    @Transactional
    public MemberDeleteResponseDto deleteMember(Long memberId, AuthInfo authInfo) {

        // 검증
        if (!authInfo.isOwnerOf(memberId)
            && !authInfo.isAdmin()) {
            throw new IllegalArgumentException("접근 불가입니다.");
        }

        // 회원 조회
        Member foundMember = memberRepository.findByIdAndIsDeletedFalse(memberId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        // 회원 삭제
        foundMember.delete();

        MemberDeleteResponseDto memberResponseDto = new MemberDeleteResponseDto(foundMember.getId());
        return memberResponseDto;
    }
}
