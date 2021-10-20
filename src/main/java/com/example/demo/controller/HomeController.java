package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.domain.Post_image;
import com.example.demo.service.MemberService;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    private final MemberService memberService;

    @Value("${file.dir}")
    private String fileDir;


    @GetMapping("/")
    public String goHomeV2(@SessionAttribute(name= "loginMember", required = false)
                           Member loginMember, Model model, HttpServletRequest request){
        if(loginMember == null){
            return "index";
        }

        model.addAttribute("posting", postService.findByUserIdOrderByIdDesc(loginMember.getId()));
        model.addAttribute("img", postService.findByPostid());
        model.addAttribute("member", loginMember);
        return "petmily/home";
    }

    @GetMapping("/petmily/profile/{id}")
    public String profile(@SessionAttribute(name = "loginMember", required = false)
    Member loginMember,@PathVariable Long id, Model model){
        if(loginMember == null){
            return "member/loginMember";
        }

        model.addAttribute("member", memberService.find(id));
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
        String path = fileDir + loginMember.getUserId();

        File file = new File(path);
        log.info("loginMember={}",loginMember.getUserId());
        log.info("path={}",path);
        if(!file.exists()){
            file.mkdirs();
        }

        Post post = new Post();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String description = request.getParameter("description");
        String location = request.getParameter("location");

        post.setDescription(description);
        post.setLocation(location);
        post.setMember(loginMember);
        post.setCreate_date(timestamp);

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

    @ResponseBody
    @GetMapping("/images/{userId}/{filename}")
    public Resource downloadImage(@PathVariable String filename, @PathVariable String userId) throws MalformedURLException {
        return new UrlResource("file:" +fileDir+ userId +"\\"+ filename);
    }

    private String rnd(String originalName, byte[] fileData, String path) throws Exception {
        UUID uuid = UUID.randomUUID();
        String savedName = uuid.toString() + "_" + originalName;
        File target = new File(path, savedName);

        FileCopyUtils.copy(fileData, target);
        return savedName;
    }
}
