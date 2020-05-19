package com.lim.poly.project2.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByuId(String uId);

    List<Member> findByEmail(String email);
}
