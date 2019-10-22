package com.itechnews.controller.web;


import com.itechnews.entity.PasswordResetToken;
import com.itechnews.entity.Role;
import com.itechnews.entity.User;
import com.itechnews.entity.VerificationToken;
import com.itechnews.repository.UserRepository;
import com.itechnews.service.PasswordResetTokenService;
import com.itechnews.service.UserService;
import com.itechnews.service.VerificationTokenService;
import com.sun.tracing.dtrace.ModuleAttributes;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.internet.MimeMessage;
import java.security.Principal;
import java.util.*;


@Controller
public class AuthController {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("login")
    public String login(Principal principal) {
        if (principal != null) { //check user logged in?
            String username = principal.getName();
            User loggedUser = userRepository.findOneByUsername(username);
            if (loggedUser != null && loggedUser.getStatus()) {
                if (loggedUser.getRole().getName().contains("ADMIN")) {
                    return "redirect:/admin/dashboard";
                } else if (loggedUser.getRole().getName().contains("USER")) {
                    return "redirect:/";
                }
            }
        }
        return "auth/login";
    }

    @GetMapping("forgot")
    public String displayForgotPasswordPage() {
        return "auth/forgot";
    }

    @PostMapping("forgot")
    public String processForgotPasswordForm(@RequestParam("email") String email, ModelMap modelMap) {
        User user = userService.findOneByEmail(email);
        if (user == null) {
            modelMap.addAttribute("errorMessage",
                    "We didn't find an account for that e-mail address.");
        } else {
            if (!user.getStatus()) {
                modelMap.addAttribute("errorMessage", "Your acc is deactivate");
            } else {
                PasswordResetToken passwordResetToken = new PasswordResetToken();
                Date expiryDate = Calendar.getInstance().getTime();
                expiryDate.setTime(expiryDate.getTime() + PasswordResetToken.EXPIRATION);
                passwordResetToken.setExpiryDate(expiryDate);

                String token = UUID.randomUUID().toString();
                passwordResetToken.setToken(token);
                passwordResetToken.setUser(user);

                passwordResetTokenService.deleteByUserId(user.getId());
                passwordResetTokenService.save(passwordResetToken);

                Map<String, Object> model = new HashMap<>();
                model.put("user", user);
                model.put("link", appUrl + "/reset?token=" + token);
                try {
                    Template template = freemarkerConfig.getTemplate("forgot.ftl");
                    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setFrom("tigersama2205@gmail.com");
                    mimeMessageHelper.setTo(user.getEmail());
                    mimeMessageHelper.setSubject("[itechnews] Forgot password");
                    mimeMessageHelper.setText(text, true);
                    javaMailSender.send(mimeMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                modelMap.addAttribute("successMessage",
                        "A password reset link has been sent to " + user.getEmail());
            }
        }
        return "auth/forgot";
    }

    @GetMapping("reset")
    public String displayResetPasswordPage(@RequestParam("token") String token, ModelMap modelMap) {

        PasswordResetToken passwordResetToken = passwordResetTokenService.findOneByToken(token);
        if (passwordResetToken == null) {
            modelMap.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");
        } else {
            User user = passwordResetToken.getUser();
            if (user != null) { // Token found in DB
                Calendar cal = Calendar.getInstance();
                if ((cal.getTime().getTime()
                    - passwordResetToken.getExpiryDate().getTime())
                    <= 0) {
                    modelMap.addAttribute("errorMessage", "Token is expired");
                } else {
                    modelMap.addAttribute("resetToken", token);
                }
            } else {
                modelMap.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");
            }
        }
        return "auth/reset";
    }

    @PostMapping("reset")
    public String setNewPassword(@RequestParam("token") String token, @RequestParam("password") String password,
                                 RedirectAttributes ra, ModelMap modelMap) {
        // Find the user associated with the reset token
        PasswordResetToken passwordResetToken = passwordResetTokenService.findOneByToken(token);
        if (passwordResetToken == null) {
            modelMap.addAttribute("errorMessage",
                    "Oops!  This is an invalid password reset link.");
            return "auth/reset";
        } else {
            User user = passwordResetToken.getUser();
            // This should always be non-null but we check just in case
            if (user != null) {

                user.setPassword(passwordEncoder.encode(password));
                // Save user
                userService.save(user);
                passwordResetTokenService.deleteByUserId(user.getId());
                // In order to set a model attribute on a redirect, we must use
                // RedirectAttributes
                ra.addFlashAttribute("successMessage", "You have successfully reset your password. " +
                        "You may now login.");

                return "redirect:login";

            } else {
                modelMap.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");
                return "auth/reset";
            }
        }
    }

    @GetMapping("register")
    public String displayRegisterPage() {
        return "auth/register";
    }

    @PostMapping("register")
    public String processRegisterForm(@ModelAttribute("user") User user, ModelMap modelMap) {
        User databaseUser = userService.findOneByUsername(user.getUsername());
        //validate
        if (databaseUser != null) {
            return "auth/register";
        }
        VerificationToken verificationToken = new VerificationToken();
        Date expiryDate = Calendar.getInstance().getTime();
        expiryDate.setTime(expiryDate.getTime() + VerificationToken.EXPIRATION);
        verificationToken.setExpiryDate(expiryDate);

        String token = UUID.randomUUID().toString();
        verificationToken.setToken(token);
        user.setStatus(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(new Role(2, null, null));
        verificationToken.setUser(user);
        verificationTokenService.deleteByUserId(user.getId());
        verificationTokenService.save(verificationToken);

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("link", appUrl + "/verify?token=" + token);
        try {
            Template template = freemarkerConfig.getTemplate("verify.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("tigersama2205@gmail.com");
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject("[itechnews] Verification account");
            mimeMessageHelper.setText(text, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelMap.addAttribute("successMessage",
                "A verification link has been sent to " + user.getEmail());
        return "auth/register";
    }

    @GetMapping("verify")
    public String verifyAccount(@RequestParam("token") String token, RedirectAttributes ra, ModelMap modelMap) {
        // Find the user associated with the reset token
        VerificationToken verificationToken = verificationTokenService.findOneByToken(token);
        if (verificationToken == null) {
            modelMap.addAttribute("errorMessage",
                    "Oops!  This is an invalid verification link.");
            return "auth/login";
        } else {
            User user = verificationToken.getUser();
            if (user != null) {
                user.setStatus(true);
                // Save user
                userService.save(user);
                verificationTokenService.deleteByUserId(user.getId());
                ra.addFlashAttribute("successMessage",
                        "You have successfully confirm your password. " +
                        "You may now login.");
                return "redirect:login";

            } else {
                modelMap.addAttribute("errorMessage",
                        "Oops!  This is an invalid verification link.");
                return "auth/login";
            }
        }
    }
}
