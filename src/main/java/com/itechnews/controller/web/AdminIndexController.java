package com.itechnews.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminIndexController {

    @GetMapping("dashboard")
    public String dashboard() {
        return "admin/index/index";
    }
}
