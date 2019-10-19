package com.itechnews.interceptor;


import com.itechnews.entity.User;
import com.itechnews.repository.UserRepository;
import com.itechnews.security.UserDetailsImpl;
import com.itechnews.security.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CheckingLoggedUserInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        try {
            //ModelMap modelMap = modelAndView.getModelMap();
            UserDetailsImpl currentLoggedUser = UserDetailsUtil.getUserDetails();
            if (currentLoggedUser != null) {
                User databaseUser = userRepository.findOneByUsername(currentLoggedUser.getUsername());
                if (databaseUser != null) {
                    if (!databaseUser.getStatus()) {
                        response.sendRedirect(request.getContextPath() + "/login");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {
    }
}
