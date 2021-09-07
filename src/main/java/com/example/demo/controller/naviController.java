package com.example.demo.controller;


import com.example.demo.service.apiService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/navigation")
@RequiredArgsConstructor
public class naviController {

    private final apiService apiService;

    @GetMapping("/navigate")
    private String navigate(){
        return "/navigation/navigate";
    }

    @GetMapping("a_navigate")
    public String navigate(Model model){
        model.addAttribute("cctvs", apiService.findcctvs());
        return "navigation/a_navigate";
    }

}
