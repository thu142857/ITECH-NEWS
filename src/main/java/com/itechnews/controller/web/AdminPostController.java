package com.itechnews.controller.web;

import com.itechnews.constant.MessageContant;
import com.itechnews.constant.MessageEnum;
import com.itechnews.entity.Category;
import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.service.CategoryService;
import com.itechnews.service.PostService;
import com.itechnews.service.TagService;
import com.itechnews.service.UserService;
import com.itechnews.util.FileUtil;
import com.itechnews.util.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin/post")
public class AdminPostController {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagService tagService;
    @Autowired
    private ServletContext servletContext;
    @ModelAttribute
    public void commonObject(ModelMap modelMap) {
        List<Category> categories =  categoryService.findAllByOrderById();
        List<Tag> tags = tagService.findAllByStatusTrue();
        modelMap.addAttribute("linkActive", "post");
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("tags", tags);
    }

    @GetMapping("index")
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) Integer page,
                        ModelMap modelMap) {
        if (page == null) {
            page = 1;
        }
        if (q == null) {
            Page<Post> postPage = postService.findAll(page);
            modelMap.addAttribute("postPage", postPage);
            modelMap.addAttribute("uri", "/admin/post/index");
        } else {
            Page<Post> postPage = postService.findAllByTitleContains(q, page);
            modelMap.addAttribute("q", q);
            modelMap.addAttribute("postPage", postPage);
            modelMap.addAttribute("uri", "/admin/post/index?q="+q);
        }
        return "admin/post/index";
    }
    @GetMapping("add")
    public String add(Post post) {
        return "admin/post/add";
    }

    @PostMapping("add")
    public String add(Principal principal,
                      @RequestParam(required = false) MultipartFile fileData,
                      @Valid @ModelAttribute("post") Post post,
                      BindingResult errors, RedirectAttributes ra) throws FileNotFoundException {
        post.setTitle(post.getTitle().trim());
        post.setContent(post.getContent().trim());
        post.setCategory(categoryService.findCategoryById(post.getCategory().getId()));
        post.setTotalViews(0);
        post.setCreateAt(new Date());
        post.setSlug(SlugUtil.makeSlug(post.getTitle()));
        post.setPostedUser(userService.findOneByUsername(principal.getName()));

        //upload image
            // Thư mục gốc upload file.
        String uploadRootPath = ResourceUtils.getURL("classpath:static/upload").getPath();
        File uploadRootDir = new File(uploadRootPath);
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        // Tên file gốc tại Client.
        String name = fileData.getOriginalFilename();
        if (name != null && name.length() > 0) {
            name = FileUtil.rename(name);
            try {
                fileData.transferTo(new File(uploadRootDir.getAbsolutePath() + File.separator + name));
                post.setImage(name);
            } catch (Exception e) {
            }
        }

        if (errors.hasErrors()) {
            return "admin/post/add";
        }
        Post savedPost = postService.save(post);
        ra.addFlashAttribute(MessageContant.ATTRIBUTE_NAME, MessageEnum.MSG_ADDED_SUCCESSFULLY);
        return "redirect:/admin/post/detail/" + savedPost.getId();
    }

    @GetMapping("detail/{id}")
    public String index(@PathVariable("id") Integer postId,
                        ModelMap modelMap) {
        Post post = postService.findOneById(postId);
        modelMap.addAttribute("post", post);
        return "admin/post/detail";
    }
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer postId,
                       ModelMap modelMap) {
        Post post = postService.findOneById(postId);
        modelMap.addAttribute("post", post);
        return "admin/post/edit";
    }

    @PostMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer postId, @RequestParam(required = false) MultipartFile fileData,
                       @Valid @ModelAttribute("post") Post post, BindingResult errors, RedirectAttributes ra) throws FileNotFoundException {
        Post oldPost = postService.findOneById(postId);
        oldPost.setTitle(post.getTitle().trim());
        oldPost.setContent(post.getContent().trim());
        oldPost.setCategory(categoryService.findCategoryById(post.getCategory().getId()));
        oldPost.setSlug(SlugUtil.makeSlug(post.getTitle()));

        //upload image
        // Thư mục gốc upload file.
        String uploadRootPath = ResourceUtils.getURL("classpath:static/upload").getPath();
        File uploadRootDir = new File(uploadRootPath);
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        // Tên file gốc tại Client.
        String name = fileData.getOriginalFilename();
        if (name != null && name.length() > 0) {
            name = FileUtil.rename(name);
            try {
                fileData.transferTo(new File(uploadRootDir.getAbsolutePath() + File.separator + name));
                oldPost.setImage(name);
            } catch (Exception e) {
            }
        }
        if (errors.hasErrors()) {
            return "admin/post/edit";
        }
        postService.save(oldPost);
        ra.addFlashAttribute(MessageContant.ATTRIBUTE_NAME, MessageEnum.MSG_UPDATED_SUCCESSFULLY);
        return "redirect:/admin/post/detail/" + oldPost.getId();
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer postId, RedirectAttributes ra) {
        postService.deleteById(postId);
        ra.addFlashAttribute(MessageContant.ATTRIBUTE_NAME, MessageEnum.MSG_DELETED_SUCCESSFULLY);
        return "redirect:/admin/post/index";
    }
}
