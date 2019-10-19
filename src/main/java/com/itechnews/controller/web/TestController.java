package com.itechnews.controller.web;

import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    TagRepository tagRepository;

    @GetMapping("/1")
    @ResponseBody
    public String test() {
        List<Tag> tags = tagRepository.findTest();
        Page<Tag> page = tagRepository.findAll(PageRequest.of(4, 10));
        List<Tag> ts = page.getContent();

        //make a break point to debug above code
        return "";
    }
}
