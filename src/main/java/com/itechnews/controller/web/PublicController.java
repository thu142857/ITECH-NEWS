package com.itechnews.controller.web;

import com.itechnews.entity.Category;
import com.itechnews.entity.Comment;
import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.exception.ResourceNotFoundException;
import com.itechnews.repository.CategoryRepository;
import com.itechnews.repository.PostRepository;
import com.itechnews.repository.TagRepository;
import com.itechnews.service.CommentService;
import com.itechnews.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class PublicController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

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

    @GetMapping("{postSlug}")
    public String postDetail(@PathVariable("postSlug") String postSlug, ModelMap modelMap) {
        Post post = postService.findOneBySlug(postSlug);
        if (post == null) {
            throw new ResourceNotFoundException();
        }
        List<Comment> parentComments = commentService.findByParentIsNullAndPostId(post.getId());
        modelMap.addAttribute("post", post);
        modelMap.addAttribute("parentComments", parentComments);
        modelMap.addAttribute("title", post.getTitle());
        return "public/detail";
    }

    @GetMapping("profile")
    public String profile() {

        return "public/profile";
    }
}
