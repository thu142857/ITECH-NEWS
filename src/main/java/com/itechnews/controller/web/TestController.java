package com.itechnews.controller.web;

import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
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

    @GetMapping("/1")
    @ResponseBody
    public String test() {
        List<Tag> tags = tagRepository.findBestTags(PageRequest.of(0, 3));
        Page<Tag> page = tagRepository.findAll(PageRequest.of(4, 10));
        List<Tag> ts = page.getContent();
        User user = userRepository.findOneByUsername("sonthh");
        //make a break point here to debug above code

        return "";
    }
}
