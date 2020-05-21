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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

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
    @PostMapping("/member/signup/checkId")
    public @ResponseBody
    int checkId(@RequestBody String uId) {
        log.info(this.getClass().getName() + ".checkId start!");

        int count = memberService.countMemberByUId(uId);

        log.info(this.getClass().getName() + ".checkId end!");
        if (count == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    //회원가입 유효성 검사 - 이메일
    @PostMapping("/member/signup/checkEmail")
    public @ResponseBody
    int checkEmail(@RequestBody String email) {
        log.info(this.getClass().getName() + ".checkEmail start!");

        int count = memberService.countMemberByEmail(email);

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

    @GetMapping("/member/login/success")
    public String loginSuccess(Principal principal, HttpSession httpSession) {
        httpSession.setAttribute("userId", principal.getName());
        return "redirect:/";
    }

    @GetMapping("/user/logout/success")
    public String logoutSuccess(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }

    //아이디 찾기 form으로 이동
    @GetMapping("member/findId")
    public String findId() {
        return "member/findId";
    }

    @PostMapping("member/findId")
    public @ResponseBody String findIdAjax(@RequestBody String email) {
        String idByEmail = memberService.findIdByEmail(email);
        if (idByEmail == null) {
            return "null";
        }
        return idByEmail;
    }
}
