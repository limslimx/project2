package com.lim.poly.project2.service;

import com.lim.poly.project2.domain.Member;
import com.lim.poly.project2.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long signUp(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findById = memberRepository.findByuId(member.getUId());
        List<Member> findByEmail = memberRepository.findByEmail(member.getEmail());

        if (!(findById.isEmpty() && findByEmail.isEmpty())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
