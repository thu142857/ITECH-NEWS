package com.itechnews.controller.web;

import com.itechnews.constant.MessageContant;
import com.itechnews.constant.MessageEnum;
import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import com.itechnews.repository.UserRepository;
import com.itechnews.validator.UserEditingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("admin/user")
public class AdminUserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEditingValidator userEditingValidator;

    @ModelAttribute
    public void commonObject(ModelMap modelMap) {
        modelMap.addAttribute("linkActive", "user");
    }

    @GetMapping("index")
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) Integer page,
                        ModelMap modelMap) {
        if (page == null) {
            page = 1;
        }
        if (q == null) {
            Page<User> userPage = userRepository.findAll(PageRequest.of(page-1,6));
            modelMap.addAttribute("userPage", userPage);
            modelMap.addAttribute("uri", "/admin/user/index");
        } else {
            Page<User> userPage = userRepository.findAllByDisplayedNameContains(q, PageRequest.of(page-1,6));
            modelMap.addAttribute("q", q);
            modelMap.addAttribute("userPage", userPage);
            modelMap.addAttribute("uri", "/admin/user/index?q="+q);
        }
        return "admin/user/index";
    }

    @GetMapping("detail/{id}")
    public String index(@PathVariable("id") Integer userId,
                        ModelMap modelMap) {
        User user = userRepository.findUserById(userId);
        modelMap.addAttribute("user", user);
        return "admin/user/detail";
    }
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer userId,
                       ModelMap modelMap) {
        User user = userRepository.findUserById(userId);
        modelMap.addAttribute("user", user);
        return "admin/user/edit";
    }

    @PostMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer userId,
                       @Valid @ModelAttribute("user") User user, BindingResult errors, RedirectAttributes ra) {
        User oldUser = userRepository.findUserById(userId);
        user.setPassword(oldUser.getPassword());
        user.setImage(oldUser.getImage());
        user.setStatus(user.getStatus());
        user.setDisplayedName(user.getDisplayedName().trim());
        user.setEmail(user.getEmail().trim());
        user.setAddress(user.getAddress());
        userEditingValidator.validate(user, errors);
        if (errors.hasErrors()) {
            return "admin/user/edit";
        }
        userRepository.save(user);
        ra.addFlashAttribute(MessageContant.ATTRIBUTE_NAME, MessageEnum.MSG_UPDATED_SUCCESSFULLY);
        return "redirect:/admin/user/detail/" + user.getId();
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer userId, RedirectAttributes ra) {
        userRepository.deleteById(userId);
        ra.addFlashAttribute(MessageContant.ATTRIBUTE_NAME, MessageEnum.MSG_DELETED_SUCCESSFULLY);
        return "redirect:/admin/user/index";
    }
}
