package com.itechnews.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.ResourceUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class EmailSendingSystem {

    //autowire from application.properties
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendWithVelocity() throws Exception {
//        Map<String, Object> model = new HashMap<>();
//        model.put("title", "my title");
//        model.put("body", "my body");
//        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
//                "templates/emails/email.vm",
//                "UTF-8", model);
//
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//        mimeMessageHelper.setFrom("tigersama2205@gmail.com");
//        int rand = new Random().nextInt(100);
//        String address = "tigersama2205+" + rand + "@gmail.com";
//        mimeMessageHelper.setTo(address);
//        mimeMessageHelper.setSubject("email submit");
//        mimeMessageHelper.setText(text, true);
//
//        //file attachment
//        try {
//            File file = ResourceUtils.getFile("classpath:static/upload/nancy.jpg");
//            mimeMessageHelper.addAttachment("nancy-lovely.png", file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        javaMailSender.send(mimeMessage);
    }

    public void sendWithFreemarker() throws Exception {
//        Map<String, Object> model = new HashMap<>();
//        model.put("title", "my title");
//        model.put("body", "my body");
//        model.put("logo", ResourceUtils.getFile("classpath:static/upload/nancy.jpg"));
//
//        Template t = freemarkerConfig.getTemplate("email.ftl");
//        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//        mimeMessageHelper.setFrom("tigersama2205@gmail.com");
//        int rand = new Random().nextInt(100);
//        String address = "tigersama2205+" + rand + "@gmail.com";
//        mimeMessageHelper.setTo(address);
//        mimeMessageHelper.setSubject("email submit");
//        mimeMessageHelper.setText(text, true);
//
//        //file attachment
//        try {
//            File file = ResourceUtils.getFile("classpath:static/images/nancy.jpg");
//            mimeMessageHelper.addAttachment("nancy.png", file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        javaMailSender.send(mimeMessage);
    }
}
