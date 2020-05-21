package com.lim.poly.project2.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByuId(String uId);

    Member findOneByuId(String uId);

    List<Member> findByEmail(String email);

    @Query("select count(m) from Member m where m.uId=:uId")
    int countMemberByUId(@Param("uId") String uId);

    @Query("select count(m) from Member m where m.email=:email")
    int countMemberByEmail(@Param("email") String email);

    @Query("select m.uId from Member m where m.email=:email")
    String findIdByEmail(@Param("email") String email);
}
