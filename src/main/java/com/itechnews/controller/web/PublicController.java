package com.itechnews.controller.web;

import com.itechnews.entity.Category;
import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.repository.CategoryRepository;
import com.itechnews.repository.PostRepository;
import com.itechnews.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class PublicController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostRepository postRepository;

    @ModelAttribute
    public void commonObject(ModelMap modelMap) {
        List<Category> categories = (List<Category>) categoryRepository.findAllByOrderById();
        List<Post> mostRead = postRepository.findTop4ByStatusTrueOrderByTotalViewsDesc();
        modelMap.addAttribute("categoryLinkActive", true);
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("mostRead", mostRead);
    }

    @GetMapping("")
    public String index(ModelMap modelMap) {
        List<Post> postsNewest = postRepository.findTop8ByStatusTrueOrderByCreateAtDesc();
        modelMap.addAttribute("postsNewest", postsNewest);
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
}
