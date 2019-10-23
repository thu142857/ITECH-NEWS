package com.itechnews.controller.web;

import com.itechnews.constant.MessageContant;
import com.itechnews.constant.MessageEnum;
import com.itechnews.entity.Tag;
import com.itechnews.service.TagService;
import com.itechnews.util.SlugUtil;
import com.itechnews.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

    @GetMapping("detail/{id}")
    public String index(@PathVariable("id") Integer tagId,
                        ModelMap modelMap) {
        Tag tag = tagService.findOneById(tagId);
        modelMap.addAttribute("tag", tag);
        return "admin/tag/detail";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer tagId, RedirectAttributes ra) {
        tagService.deleteById(tagId);

        ra.addFlashAttribute(MessageContant.ATTRIBUTE_NAME, MessageEnum.MSG_DELETED_SUCCESSFULLY);
        return "redirect:/admin/tag/index";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer tagId,
                        ModelMap modelMap) {
        Tag tag = tagService.findOneById(tagId);
        modelMap.addAttribute("tag", tag);
        return "admin/tag/edit";
    }

    @PostMapping("edit/{id}")
    public String edit(@Valid @ModelAttribute("tag") Tag tag, BindingResult errors, RedirectAttributes ra) {
        tagValidator.validate(tag, errors);
        if (errors.hasErrors()) {
            return "admin/tag/edit";
        }
        tag.setSlug(SlugUtil.makeSlug(tag.getName()));
        tagService.save(tag);
        ra.addFlashAttribute(MessageContant.ATTRIBUTE_NAME, MessageEnum.MSG_UPDATED_SUCCESSFULLY);
        return "redirect:/admin/tag/detail/" + tag.getId();
    }

    @GetMapping("add")
    public String add(Tag tag) {
        return "admin/tag/add";
    }

    @Autowired
    private TagValidator tagValidator;

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("tag") Tag tag, BindingResult errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return "admin/tag/add";
        }
        Tag savedTag = tagService.save(tag);
        return "redirect:/admin/tag/detail/" + savedTag.getId();
    }
}
