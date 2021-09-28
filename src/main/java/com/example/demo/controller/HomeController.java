package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.domain.Post_image;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;

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
        model.addAttribute("posting", postService.findByUserIdOrderByIdDesc(loginMember.getId()));
        model.addAttribute("img", postService.findByPostid());
        model.addAttribute("member", loginMember);
        return "petmily/home";
    }

    @GetMapping("/petmily/home")
    public String goHome(@SessionAttribute(name = "loginMember", required = false)
                                     Member loginMember, Model model) {
        if(loginMember == null){
            return "member/loginMember";
        }

        model.addAttribute("posting", postService.findByUserIdOrderByIdDesc(loginMember.getId()));
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
        model.addAttribute("post", new Post());
        model.addAttribute("loginMember", loginMember);
        return "petmily/upload";
    }

    @PostMapping("/petmily/upload")
    public String upload(HttpServletRequest request, MultipartHttpServletRequest mtpRequest, Model model) throws Exception {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member)session.getAttribute("loginMember");
        String path = "C:\\Users\\sjj02\\Desktop\\project\\project\\src\\main\\resources\\static\\images\\" + loginMember.getUserId();

        File file = new File(path);
        log.info("loginMember={}",loginMember.getUserId());
        log.info("path={}",path);
        if(!file.exists()){
            file.mkdirs();
        }

        Post post = new Post();
        String description = request.getParameter("description");
        String location = request.getParameter("location");

        post.setDescription(description);
        post.setLocation(location);
        post.setMember(loginMember);

        post.setId(postService.save(post));

        List<MultipartFile> fileList = mtpRequest.getFiles("files");
        for(MultipartFile f : fileList){
            Post_image pi = new Post_image();

            String originalFilename = f.getOriginalFilename();
            pi.setFileorImage(originalFilename);

            String newFilename = rnd(originalFilename, f.getBytes(), path);
            pi.setFilename(newFilename);
            pi.setPostId(post.getId());

            postService.piSave(pi);
        }

        return "redirect:/";

    }

    private String rnd(String originalName, byte[] fileData, String path) throws Exception {
        UUID uuid = UUID.randomUUID();
        String savedName = uuid.toString() + "_" + originalName;
        File target = new File(path, savedName);

        FileCopyUtils.copy(fileData, target);
        return savedName;
    }
}
