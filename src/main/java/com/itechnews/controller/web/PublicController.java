package com.itechnews.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechnews.entity.*;
import com.itechnews.exception.ResourceNotFoundException;
import com.itechnews.repository.CategoryRepository;
import com.itechnews.repository.PostRepository;
import com.itechnews.security.UserDetailsImpl;
import com.itechnews.security.UserDetailsServiceImpl;
import com.itechnews.security.UserDetailsUtil;
import com.itechnews.service.CommentService;
import com.itechnews.service.PostService;
import com.itechnews.service.TagService;
import com.itechnews.service.UserService;
import com.itechnews.util.SlugUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class PublicController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute
    public void commonObject(ModelMap modelMap) {
        List<Category> categories = categoryRepository.findAllByOrderById();
        List<Post> mostRead = postRepository.findTop5ByStatusTrueOrderByTotalViewsDesc();
        List<Tag> bestTags = tagService.findBestTags();
        modelMap.addAttribute("categoryLinkActive", true);
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("mostRead", mostRead);
        modelMap.addAttribute("bestTags", bestTags);
    }

    @GetMapping("")
    public String postPage(ModelMap modelMap) {
        Page<Post> pageNewPosts = postService.findNewPosts(0);
        modelMap.addAttribute("pageNewPosts", pageNewPosts);

        Page<Post> pageTopPosts = postService.findTopPosts(0);
        modelMap.addAttribute("pageTopPosts", pageTopPosts);

        List<Tag> topTags = tagService.findTopTags(15);
        modelMap.addAttribute("topTags", topTags);

        List<User> topUsers = userService.findTopUsers(8);
        modelMap.addAttribute("topUsers", topUsers);

        return "public/posts";
    }

    @GetMapping("search")
    public String searchPage(@RequestParam(value = "q", required = false) String q, ModelMap modelMap) {
        boolean isSearch = true;
        if (q == null || q.trim().equals("")) {
            isSearch = false;
            return "public/search";
        }


        modelMap.addAttribute("searchText", q);
        Page<Post> pageSearchPosts = postService.searchByTitle(q, 0);
        modelMap.addAttribute("pageSearchPosts", pageSearchPosts);

        modelMap.addAttribute("isSearch", isSearch);
        modelMap.addAttribute("title", "Tìm kiếm: " + q);
        return "public/search";
    }

    @GetMapping("tag/{tagSlug}")
    public String tagPage(@PathVariable("tagSlug") String tagSlug, ModelMap modelMap) {
        Tag tag = tagService.findBySlug(tagSlug);
        if (tag == null) {
            throw new ResourceNotFoundException();
        }
        Page<Post> postPage = postService.findByTagContains(tag, 0);
        modelMap.addAttribute("postPage", postPage);
        modelMap.addAttribute("tag", tag);
        modelMap.addAttribute("title", "#" + tag.getName());
        return "public/tags";
    }

    /*@GetMapping("index2")
    public String index(ModelMap modelMap) {
        List<Integer> ids = new ArrayList<>();
        List<Post> postsTech = postService.findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(1);
        for (Post post : postsTech) {
            ids.add(post.getId());
        }
        modelMap.addAttribute("postsTech", postsTech);
        List<Post> postsBlog = postService.findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(4);
        for (Post post : postsBlog) {
            ids.add(post.getId());
        }
        modelMap.addAttribute("postsBlog", postsBlog);
        List<Post> postsDev = postService.findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(2);
        for (Post post : postsDev) {
            ids.add(post.getId());
        }
        modelMap.addAttribute("postsDev", postsDev);

        List<Post> postsNewest = postRepository.findTop8ByStatusTrueAndIdNotInOrderByCreateAtDesc(ids);
        modelMap.addAttribute("postsNewest", postsNewest);

        return "public/index";
    }*/

    /*@GetMapping("blog-post")
    public String blogPost(ModelMap modelMap) {
        modelMap.addAttribute("title", "hoc-lap-trinh-java");
        return "public/blog_post";
    }*/

    /*@GetMapping("tag")
    public String category() {

        return "public/tag";
    }*/

    /*@GetMapping("contact")
    public String contact() {

        return "public/contact";
    }*/

    /*@GetMapping("about")
    public String about() {

        return "public/about";
    }

    @GetMapping("blank")
    public String blank() {

        return "public/blank";
    }*/

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


        Cookie cookie = WebUtils.getCookie(request, "post-" + post.getId());
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

    @GetMapping("user/{username}/edit")
    public String updateProfilePage(@PathVariable("username") String username, ModelMap modelMap) {
        User user = userService.findOneByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        UserDetails userDetails = null;
        try {
            userDetails = UserDetailsUtil.getUserDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user.getId() != ((UserDetailsImpl) userDetails).getId()) {
            throw new ResourceNotFoundException();
        }

        modelMap.addAttribute("user", user);
        modelMap.addAttribute("title", user.getUsername());
        return "public/profile_edit";
    }

    @PostMapping("user/{username}/edit")
    public String updateProfilePost(@PathVariable("username") String username,
                                    @RequestParam("type") String type, HttpServletRequest request,
                                    @RequestParam(value = "image", required = false) CommonsMultipartFile cmf,
                                    RedirectAttributes ra) throws UnsupportedEncodingException {
        User user = userService.findOneByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException();
        }
        if (type.equals("UPDATE_IMAGE")) {

            String originalFilename = cmf.getOriginalFilename();
            if (originalFilename != null && !originalFilename.equals("")) {
                final String DIR_PATH = servletContext.getRealPath("") + "upload";
                File dir = new File(DIR_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileName = user.getUsername() + "-" + FilenameUtils.getBaseName(originalFilename)
                        + "-" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(originalFilename);
                File file = new File(dir.getAbsolutePath() + File.separator + fileName);
                try {
                    cmf.transferTo(file);

                    File uploadIntoProject = new File(
                            new File("src/main/resources/static/upload").getAbsolutePath()
                    );
                    FileUtils.copyFileToDirectory(file, uploadIntoProject);

                    user.setImage(fileName);
                    userService.save(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //update UserDetail
            UserDetailsUtil.updateUserDetail(user, request);
            ra.addFlashAttribute("message", "Chỉnh sửa thông tin thành công");
        }else if (type.equals("UPDATE_INFO")) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            if (name.length() < 3 || email.length() < 4) {
                ra.addFlashAttribute("message", "Vui lòng nhập tên và email hợp lệ");
            } else {
                user.setDisplayedName(name);
                user.setEmail(email);
                userService.save(user);

                //update UserDetail
                UserDetailsUtil.updateUserDetail(user, request);
                ra.addFlashAttribute("message", "Chỉnh sửa thông tin thành công");
            }
        } else if (type.equals("UPDATE_PASSWORD")) {
            String password = request.getParameter("password");
            String newPassword = request.getParameter("newpassword");
            String confirmNewPassword = request.getParameter("confirmnewpassword");
            if (password.length() < 6 || password.length() < 6 || confirmNewPassword.length() < 6) {
                ra.addFlashAttribute("message", "Vui lòng nhập mật khẩu từ 6 ký tự trở lên");
            } else {
                if (passwordEncoder.matches(password, user.getPassword())) {

                    if (newPassword.equals(confirmNewPassword)) {
                        user.setPassword(passwordEncoder.encode(newPassword));
                        userService.save(user);
                        //update UserDetail
                        UserDetailsUtil.updateUserDetail(user, request);
                        ra.addFlashAttribute("message", "Chỉnh sửa thông tin thành công");
                    } else {
                        ra.addFlashAttribute("message", "Vui lòng confirm password");
                    }
                } else {
                    ra.addFlashAttribute("message", "Vui lòng nhập đúng mật khẩu cũ");
                }
            }
        }

        return "redirect:/user/"+ URLEncoder.encode(user.getUsername(), "UTF-8")+"/edit";
    }

    @GetMapping("posts/new")
    public String createNewPostPage(ModelMap modelMap) {

        List<Tag> tags = tagService.findAll();
        modelMap.addAttribute("tags", tags);
        modelMap.addAttribute("title", "Viết bài");
        return "public/posts_new";
    }

    @GetMapping("posts/edit/{postSlug}")
    public String editPostPage(@PathVariable("postSlug") String postSlug, ModelMap modelMap) {
        Post post = postService.findOneBySlug(postSlug);
        if (post == null) {
            throw new ResourceNotFoundException();
        }

        UserDetails userDetails = null;
        try {
            userDetails = UserDetailsUtil.getUserDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = userService.findOneById(((UserDetailsImpl) userDetails).getId());

        if (user.getId() != post.getPostedUser().getId()) {
            throw new ResourceNotFoundException();
        }

        List<Tag> tags = tagService.findAll();
        //loại bỏ những tag đã là tag của bài post này
        Post finalPost = post;
        tags.removeIf(tag -> {
            boolean check = false;
            for (Tag t : finalPost.getTags()) {
                if (t.getId() == tag.getId()) {
                    check = true;
                }
            }
            return check;
        });


        modelMap.addAttribute("tags", tags);
        modelMap.addAttribute("post", post);
        modelMap.addAttribute("title", "Viết bài: "+post.getTitle());

        try {
            copyCkfinder();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "public/posts_edit";
    }

    @PostMapping("posts/new")
    public String createNewPost(
            @RequestParam("title") String title,
            @RequestParam("editor") String editor,
            @RequestParam(value = "tags", required = false) List<Integer> tagIds,
            RedirectAttributes ra) {
        Post post = new Post();
        post.setTitle(title);
        post.setTotalViews(0);
        post.setContent(editor);
        List<Tag> tags = tagService.findByIdIn(tagIds);
        post.setTags(tags);
        post.setCategory(new Category(1, null, null));
        post.setCreateAt(new Date());
        post.setSlug(SlugUtil.makeSlug(post.getTitle()));
        post.setImage(null);
        post.setStatus(true);

        UserDetails userDetails = null;
        try {
            userDetails = UserDetailsUtil.getUserDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = userService.findOneById(((UserDetailsImpl) userDetails).getId());
        post.setPostedUser(user);
        post = postService.save(post);
        ra.addFlashAttribute("message", "Đăng bài thành công, xem lại những gì bạn mới viết!");


        try {
            copyCkfinder();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/posts/edit/" + post.getSlug();
    }

    public void copyCkfinder() throws IOException {
        File uploadIntoProject = new File(
                new File("src/main/resources/static/ckfinder/images").getAbsolutePath()
        );
        File file = new File(servletContext.getRealPath("") + "/ckfinder/images");
        FileUtils.copyDirectory(file, uploadIntoProject);
    }

    @PostMapping("posts/edit/{postSlug}")
    public String editPost(
            @PathVariable("postSlug") String postSlug,
            @RequestParam("title") String title,
            @RequestParam("editor") String editor,
            @RequestParam(value = "tags", required = false) List<Integer> tagIds,
            RedirectAttributes ra) {
        Post post = postService.findOneBySlug(postSlug);

        if (post == null) {
            throw new ResourceNotFoundException();
        }

        UserDetails userDetails = null;
        try {
            userDetails = UserDetailsUtil.getUserDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = userService.findOneById(((UserDetailsImpl) userDetails).getId());

        if (user.getId() != post.getPostedUser().getId()) {
            throw new ResourceNotFoundException();
        }

        post.setTitle(title);
        post.setContent(editor);

        List<Tag> tags = tagService.findByIdIn(tagIds);
        post.setTags(tags);

        post.setSlug(SlugUtil.makeSlug(post.getTitle()));
        post = postService.save(post);
        ra.addFlashAttribute("message", "Xác nhận sửa bài viết thành công, xem lại những gì bạn mới viết!");
        return "redirect:/posts/edit/" + post.getSlug();
    }
}
