package com.itechnews.security;

import com.itechnews.entity.User;
import com.itechnews.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsernameAndStatus(username, true);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        UserDetailsImpl userDetails = UserDetailsUtil.buildUserDetail(user);

        BeanUtils.copyProperties(user, userDetails);
        return userDetails;
    }



}
