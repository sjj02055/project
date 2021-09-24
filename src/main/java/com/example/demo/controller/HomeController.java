package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

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

    @GetMapping("/petmily/upload")
    public String upload(@SessionAttribute(name = "loginMember", required = false)Member loginMember, Model model){
        if(loginMember == null){
            return "member/loginMember";
        }
        model.addAttribute("member", loginMember);
        return "petmily/upload";
    }

    @PostMapping("/petmily/upload")
    public String upload(HttpServletRequest request, MultipartHttpServletRequest mtpRequest, Model model) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member)session.getAttribute("loginMember");
        String path = "C:\\Users\\sjj02\\Desktop\\project\\project\\src\\main\\resources\\static\\images\\" + loginMember.getUserId();

        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        Post post = new Post();





    }
}
