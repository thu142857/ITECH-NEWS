package com.itechnews.controller.web;

import com.itechnews.entity.Category;
import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import com.itechnews.repository.CategoryRepository;
import com.itechnews.repository.PostRepository;
import com.itechnews.repository.TagRepository;
import com.itechnews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/1")
    @ResponseBody
    public String test() {
        //List<Post> postsNewest = postRepository.findTop2ByStatusTrueOrderByCreateAtDesc();
        //make a break point here to debug above code

        return "";
    }
}
