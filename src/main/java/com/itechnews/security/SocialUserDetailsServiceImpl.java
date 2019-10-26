package com.itechnews.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // After user created by ConnectionSignUpImpl.execute(Connection<?>)
    // This method is called by the Spring Social API.
    @Override
    public SocialUserDetails loadUserByUserId(String userName) throws UsernameNotFoundException, DataAccessException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        SocialUserDetailsImpl socialUserDetails = new SocialUserDetailsImpl((UserDetailsImpl) userDetails);
        return socialUserDetails;
    }

}