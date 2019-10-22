package com.itechnews.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PublicController {

    @GetMapping("")
    public String index() {

        return "public/index";
    }

    @GetMapping("blog-post")
    public String blogPost(ModelMap modelMap) {
        modelMap.addAttribute("title", "hoc-lap-trinh-java");
        return "public/blog_post";
    }

    @GetMapping("tag")
    public String category() {

        return "public/tag";
    }

    @GetMapping("contact")
    public String contact() {

        return "public/contact";
    }

    @GetMapping("about")
    public String about() {

        return "public/about";
    }

    @GetMapping("blank")
    public String blank() {

        return "public/blank";
    }

    @GetMapping("profile")
    public String profile() {

        return "public/profile";
    }
}
