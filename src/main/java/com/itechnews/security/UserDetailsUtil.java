package com.itechnews.security;

import com.itechnews.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
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

    public static UserDetailsImpl buildUserDetail(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getName());
        authorities.add(authority);

        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getUsername(),
                user.getDisplayedName(),
                user.getPassword(),
                user.getImage(),
                user.getStatus(), user.getRole(), authorities);
        return  userDetails;
    }

    public static void updateUserDetail(User user, HttpServletRequest request) {
        UserDetailsImpl userDetail = UserDetailsUtil.buildUserDetail(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
