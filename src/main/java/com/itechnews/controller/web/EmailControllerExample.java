package com.itechnews.controller.web;

import com.itechnews.email.EmailSendingSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("email")
public class EmailControllerExample {

    @Autowired
    private EmailSendingSystem emailSendingSystem;

    @GetMapping("send")
    @ResponseBody
    public String test1() {
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                try {
                    emailSendingSystem.sendWithVelocity();
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return "OK";
    }

    @GetMapping("send2")
    @ResponseBody
    public String test2() {
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                try {
                    emailSendingSystem.sendWithFreemarker();
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return "OK";
    }

}
