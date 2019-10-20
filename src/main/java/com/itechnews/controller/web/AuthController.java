package com.itechnews.controller.web;


import com.itechnews.entity.User;
import com.itechnews.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class AuthController {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("login")
    public String login(Principal principal) {
        if (principal != null) { //check user logged in?
            String username = principal.getName();
            User loggedUser = userRepository.findOneByUsername(username);
            if (loggedUser != null && loggedUser.getStatus())
                return "redirect:/admin/dashboard";
        }
        return "auth/login";
    }
}
