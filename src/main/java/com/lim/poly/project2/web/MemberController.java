package com.lim.poly.project2.web;

import com.lim.poly.project2.domain.MemberRepository;
import com.lim.poly.project2.service.MemberService;
import com.lim.poly.project2.web.dto.MemberSignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //회원가입 form으로 이동
    @GetMapping("/member/signup")
    public String signupForm(Model model) {
        model.addAttribute("memberSignUpForm", new MemberSignUpForm());
        return "member/signup";
    }

    //백엔드 유효성 검사 회원가입 정보 등록
    @PostMapping("/member/signup")
    public String createMember(@Valid MemberSignUpForm memberSignUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signup";
        }
        memberService.signUp(memberSignUpForm);

        return "redirect:/member/login";
    }

    //회원가입 유효성 검사 - 아이디
    @PostMapping("/user/signup/checkId")
    public @ResponseBody
    int checkId(@RequestBody String uId) {
        log.info(this.getClass().getName() + ".checkId start!");

        int count = memberRepository.countMemberByUId(uId);
        log.info(this.getClass().getName() + ".checkId end!");
        if (count == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    //회원가입 유효성 검사 - 이메일
    @PostMapping("/user/signup/checkEmail")
    public @ResponseBody int checkEmail(@RequestBody String email) {
        log.info(this.getClass().getName() + ".checkEmail start!");

        int count = memberRepository.countMemberByEmail(email);
        log.info(this.getClass().getName() + ".checkEmail end!");
        if (count == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    //로그인 form으로 이동
    @GetMapping("member/login")
    public String login() {
        return "member/login";
    }

}
