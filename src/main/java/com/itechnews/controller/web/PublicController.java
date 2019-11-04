package com.itechnews.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechnews.entity.*;
import com.itechnews.exception.ResourceNotFoundException;
import com.itechnews.repository.CategoryRepository;
import com.itechnews.repository.PostRepository;
import com.itechnews.security.UserDetailsImpl;
import com.itechnews.security.UserDetailsUtil;
import com.itechnews.service.CommentService;
import com.itechnews.service.PostService;
import com.itechnews.service.TagService;
import com.itechnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void commonObject(ModelMap modelMap) {
        List<Category> categories = (List<Category>) categoryRepository.findAllByOrderById();
        List<Post> mostRead = postRepository.findTop5ByStatusTrueOrderByTotalViewsDesc();
        List<Tag> bestTags = tagService.findBestTags();
        modelMap.addAttribute("categoryLinkActive", true);
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("mostRead", mostRead);
        modelMap.addAttribute("bestTags", bestTags);
    }

    @GetMapping("posts")
    public String postPage(ModelMap modelMap) {
        return "public/posts";
    }

    @GetMapping("")
    public String index(ModelMap modelMap) {
        List<Integer> ids = new ArrayList<>();
        List<Post> postsTech = postService.findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(1);
        for ( Post post:postsTech) {
            ids.add(post.getId());
        }
        modelMap.addAttribute("postsTech", postsTech);
        List<Post> postsBlog = postService.findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(4);
        for ( Post post:postsBlog) {
            ids.add(post.getId());
        }
        modelMap.addAttribute("postsBlog", postsBlog);
        List<Post> postsDev = postService.findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(2);
        for ( Post post:postsDev) {
            ids.add(post.getId());
        }
        modelMap.addAttribute("postsDev", postsDev);

        List<Post> postsNewest = postRepository.findTop8ByStatusTrueAndIdNotInOrderByCreateAtDesc(ids);
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
    public String postDetail(@PathVariable("postSlug") String postSlug, ModelMap modelMap,
                             HttpServletRequest request, HttpServletResponse response) {
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


        int totalOfPost = postService.countByPostedUser(post.getPostedUser());

        modelMap.addAttribute("totalOfPost", totalOfPost);
        modelMap.addAttribute("liked", liked);
        modelMap.addAttribute("post", post);
        modelMap.addAttribute("parentComments", parentComments);
        modelMap.addAttribute("title", post.getTitle());


        Cookie cookie =	WebUtils.getCookie(request, "post-" + post.getId());
        if (cookie == null) {
            post.setTotalViews(post.getTotalViews() + 1);
            postService.save(post);
            cookie = new Cookie("post-" + post.getId(), "post-" + post.getId());
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
        }

        return "public/detail";
    }

    @GetMapping("user/{username}")
    public String profilePage(@PathVariable("username") String username, ModelMap modelMap) throws JsonProcessingException {
        User user = userService.findOneByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        List<Tag> tags = tagService.findByUser(user);
        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            labels.add(tags.get(i).getName());
            int count = postService.countByPostedUserAndTagsContains(user, tags.get(i));
            data.add(count);
        }
        ObjectMapper mapper = new ObjectMapper();
        modelMap.addAttribute("labels", mapper.writeValueAsString(labels));
        modelMap.addAttribute("data", mapper.writeValueAsString(data));

        List<Post> posts = postService.findByPostedUser(user);
        modelMap.addAttribute("posts", posts);

        Integer totalOfComments = commentService.countByPostedUser(user);
        if (totalOfComments == null)
            totalOfComments = 0;
        modelMap.addAttribute("totalOfComments", totalOfComments);

        Integer totalOfViews = postService.calculateTotalViewOfUser(user);
        if (totalOfViews == null)
            totalOfViews = 0;
        modelMap.addAttribute("totalOfViews", totalOfViews);

        Integer totalOfLike = postService.calculateTotalLikeOfUser(user);
        if (totalOfLike == null)
            totalOfLike = 0;
        modelMap.addAttribute("totalOfLike", totalOfLike);

        modelMap.addAttribute("title", user.getUsername());
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("tags", tags);



        Boolean isMe = false;
        Boolean followed = false;
        try {
            UserDetails userDetails = UserDetailsUtil.getUserDetails();
            User loggedUser = userService.findOneById(((UserDetailsImpl) userDetails).getId());
            if (loggedUser.getId() == user.getId()) {
                isMe = true;
            }
            if (user.getFollower().contains(loggedUser)) {
                followed = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelMap.addAttribute("isMe", isMe);
        modelMap.addAttribute("followed", followed);

        return "public/profile";
    }

    @GetMapping("tag/{tagSlug}")
    @ResponseBody
    public String tagPage(@PathVariable("tagSlug") String tagSlug) {

        return "THIS IS TAG PAGE";
    }
}
