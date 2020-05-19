package com.lim.poly.project2.web;

import com.lim.poly.project2.domain.Member;
import com.lim.poly.project2.domain.Role;
import com.lim.poly.project2.service.MemberService;
import com.lim.poly.project2.web.dto.MemberSignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/signup")
    public String signupForm(Model model) {
        log.info(this.getClass().getName() + ".signupForm start!");
        model.addAttribute("memberSignUpForm", new MemberSignUpForm());
        return "member/signup";
    }

    @PostMapping("/member/signup")
    public String createMember(@Valid MemberSignUpForm memberSignUpForm, BindingResult bindingResult) {
        log.info(this.getClass().getName() + ".createMember start!");

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }
        Role role = null;
        if (memberSignUpForm.getEmail().contains("admin@")) {
            role = Role.ADMIN;
        } else {
            role = Role.MEMBER;
        }

        Member member = Member.builder()
                .uId(memberSignUpForm.getUId())
                .email(memberSignUpForm.getEmail())
                .password(memberSignUpForm.getPassword())
                .role(role)
                .build();

        memberService.signUp(member);

        return "redirect:/";
    }
}
