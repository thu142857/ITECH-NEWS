package com.itechnews.controller.web;

import com.itechnews.entity.Tag;
import com.itechnews.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @ModelAttribute
    public void commonObject(ModelMap modelMap) {
        modelMap.addAttribute("linkActive", "tag");
    }


    @GetMapping("index")
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) Integer page,
                        ModelMap modelMap) {
        if (q == null) {
            Page<Tag> tagPage = tagService.findAll(page);
            modelMap.addAttribute("tagPage", tagPage);
            modelMap.addAttribute("uri", "/admin/tag/index");
        } else {
            Page<Tag> tagPage = tagService.findAllByNameContains(q, page);
            modelMap.addAttribute("q", q);
            modelMap.addAttribute("tagPage", tagPage);
            modelMap.addAttribute("uri", "/admin/tag/index?q="+q);
        }
        return "admin/tag/index";
    }


}
