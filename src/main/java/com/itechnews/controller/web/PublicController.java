package com.itechnews.controller.web;

import com.itechnews.entity.*;
import com.itechnews.exception.ResourceNotFoundException;
import com.itechnews.repository.CategoryRepository;
import com.itechnews.repository.PostRepository;
import com.itechnews.repository.TagRepository;
import com.itechnews.security.UserDetailsImpl;
import com.itechnews.security.UserDetailsUtil;
import com.itechnews.service.CommentService;
import com.itechnews.service.PostService;
import com.itechnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserService userService;

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

        Boolean liked = false;
        try {
            UserDetails userDetails = UserDetailsUtil.getUserDetails();
            User user = userService.findOneById(((UserDetailsImpl) userDetails).getId());
            if (user != null) {
                if (post.getLikedUsers().contains(user)) {
                    liked = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Comment> parentComments = commentService.findByParentIsNullAndPostId(post.getId());

        modelMap.addAttribute("liked", liked);
        modelMap.addAttribute("post", post);
        modelMap.addAttribute("parentComments", parentComments);
        modelMap.addAttribute("title", post.getTitle());
        return "public/detail";
    }

    @GetMapping("user/{username}")
    public String profilePage(@PathVariable("username") String username) {

        return "public/profile";
    }

    @GetMapping("tag/{tagSlug}")
    @ResponseBody
    public String tagPage(@PathVariable("tagSlug") String tagSlug) {

        return "THIS IS TAG PAGE";
    }
}
