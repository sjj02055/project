package com.example.demo.controller;

import com.example.demo.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String goHomeV2(@SessionAttribute(name= "loginMember", required = false)
                           Member loginMember, Model model){
        if(loginMember ==null){
            return "index";
        }
        model.addAttribute("member", loginMember);
        return "petmily/home";
    }

    @GetMapping("/petmily/home")
    public String goHome(@SessionAttribute(name = "loginMember", required = false)
                                     Member loginMember, Model model) {
        if(loginMember == null){
            return "member/loginMember";
        }

        model.addAttribute("member", loginMember);
        return "/petmily/home";
    }

    @GetMapping("/petmily/profile")
    public String profile(@SessionAttribute(name = "loginMember", required = false)
    Member loginMember, Model model){

        if(loginMember == null){
            return "member/loginMember";
        }
        model.addAttribute("member", loginMember);
        return "/petmily/profile";
    }

}
