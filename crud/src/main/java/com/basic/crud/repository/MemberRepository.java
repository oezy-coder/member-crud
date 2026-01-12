package com.basic.crud.repository;

import com.basic.crud.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByIsDeletedFalse();

    Optional<Member> findByIdAndIsDeletedFalse(Long memberId);

    // 중복 이메일 여부 조회
    Boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);
}
