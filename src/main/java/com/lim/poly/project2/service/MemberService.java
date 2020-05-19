package com.lim.poly.project2.service;

import com.lim.poly.project2.domain.Member;
import com.lim.poly.project2.domain.MemberRepository;
import com.lim.poly.project2.domain.Role;
import com.lim.poly.project2.web.dto.MemberSignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member signUp(MemberSignUpForm memberSignUpForm) {
        validateDuplicateMember(memberSignUpForm);
        if (!memberSignUpForm.getPassword().equals(memberSignUpForm.getPasswordCheck())) {
            throw new IllegalStateException("비밀번호와 비밀번호확인이 일치하지 않습니다.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (memberSignUpForm.getEmail().contains("admin@")) {
            memberSignUpForm.setRole(Role.ADMIN);
        } else {
            memberSignUpForm.setRole(Role.MEMBER);
        }
        memberSignUpForm.setPassword(passwordEncoder.encode(memberSignUpForm.getPassword()));

        return memberRepository.save(memberSignUpForm.toEntity());
    }

    private void validateDuplicateMember(MemberSignUpForm memberSignUpForm) {
        List<Member> findById = memberRepository.findByuId(memberSignUpForm.getUId());
        List<Member> findByEmail = memberRepository.findByEmail(memberSignUpForm.getEmail());

        if (!(findById.isEmpty() && findByEmail.isEmpty())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String uId) throws UsernameNotFoundException {
        List<Member> byuId = memberRepository.findByuId(uId);


        return null;
    }
}
