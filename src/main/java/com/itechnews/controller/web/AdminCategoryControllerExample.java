package com.itechnews.controller.web;

import com.itechnews.entity.Tag;
import com.itechnews.repository.TagRepository;
import com.itechnews.util.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("admin/category")
public class AdminCategoryControllerExample {

    @Autowired
    TagRepository categoryRepository;

    @ModelAttribute
    public void commonObject(ModelMap modelMap) {
        modelMap.addAttribute("categoryLinkActive", true);
    }


    @GetMapping("index")
    public String index(@RequestParam(required = false) Integer page, ModelMap modelMap) {
        if (page == null) {
            page = 1;
        }
        Page<Tag> categoryPage = categoryRepository.findAll(PageRequest.of(page - 1, 3));
        //List<Tag> categories = categoryPage.getContent();
        modelMap.addAttribute("categoryPage", categoryPage);
        return "admin/category/index";
    }

    @GetMapping("add")
    public String add(Tag category) {
        return "admin/category/add";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("category") Tag category, BindingResult errors) {
        if (errors.hasErrors()) {
            return "admin/category/add";
        }
        category.setSlug(SlugUtil.makeSlug(category.getName()));
        categoryRepository.save(category);

        return "redirect:/admin/category/index";
    }

    @GetMapping("edit/{id}/page/{page}")
    public String edit(@PathVariable("id") Integer id, @PathVariable("page") Integer page, ModelMap modelMap) {
        Tag category = categoryRepository.findById(id).get();
        modelMap.addAttribute("category", category);
        modelMap.addAttribute("page", page);
        return "admin/category/edit";
    }

    @PostMapping("edit/{id}/page/{page}")
    public String edit(@Valid @ModelAttribute("category") Tag category, BindingResult errors,
                       @PathVariable("page") Integer page) {
        if (errors.hasErrors()) {
            return "admin/category/edit";
        }
        category.setSlug(SlugUtil.makeSlug(category.getName()));
        categoryRepository.save(category);
        return "redirect:/admin/category/index" + (page >  1 ? ("?page=" + page) : "");
    }

    @GetMapping("delete/{id}/page/{page}")
    public String delete(@PathVariable("id") Integer id, @PathVariable("page") Integer page) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category/index" + (page >  1 ? ("?page=" + page) : "");
    }


}
