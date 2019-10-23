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

    @GetMapping("table-basic")
    public String tableBasic() {
        return "admin/index/table_basic";
    }

    @GetMapping("table-data")
    public String tableData() {
        return "admin/index/table_data";
    }

    @GetMapping("form")
    public String form() {
        return "admin/index/form";
    }
}
