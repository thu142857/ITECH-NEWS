package com.itechnews.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsUtil {

    //static method for using at .html template
    public static UserDetailsImpl getUserDetails() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SocialUserDetailsImpl) {
            return ((SocialUserDetailsImpl) principal).getUserDetails();
        }
        return (UserDetailsImpl) principal;
    }

    public static List<String> getAuthorities() {
        List<String> results = new ArrayList<>();
        List<GrantedAuthority> authorities =
                (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            results.add(authority.getAuthority());
        }
        return results;
    }
}
