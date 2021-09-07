package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.domain.loginForm;
import com.example.demo.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/create")
    public String createMember(Model model){
        model.addAttribute("createForm", new createForm());
        log.info("createForm={}", model);
        return "member/createMember";
    }

    @PostMapping("/create")
    public String saveMember(@Valid @ModelAttribute createForm form, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("bindingresult= {}",bindingResult);
            return "member/createMember";
        }

        if(!form.getRepass().equals(form.getPassword())){
            bindingResult.reject("notEqual","비밀번호가 다릅니다.");
            return "member/createMember";
        }

        Member member = new Member();

        member.setNickname(form.getNickname());
        member.setDay(form.getDay());
        member.setMonth(form.getMonth());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setUserId(form.getUserId());
        member.setYear(form.getYear());

        log.info("members ={}", form);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm")loginForm form){
        return "member/loginMember";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute loginForm form, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "member/loginMember";
        }

        Member loginMember = memberService.login(form.getUserId(), form.getPassword());

        log.info("login = {}", loginMember);

        if(loginMember==null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/loginMember";
        }

        //로그인 성공 처리

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 정보 보관
        session.setAttribute("loginMember", loginMember);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }


    @Data
    private class createForm {

        @NotBlank(message = "닉네임 입력은 필수입니다.")
        private String nickname;

        @NotBlank(message = "ID 입력은 필수입니다.")
        private String userId;

        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        private String password;

        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        private String repass;

        @NotBlank(message = "이름 입력은 필수입니다.")
        private String name;

        private int year,month,day;

    }
}