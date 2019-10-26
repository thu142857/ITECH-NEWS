package com.itechnews.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;

public class SocialUserDetailsImpl implements SocialUserDetails {

    private UserDetailsImpl userDetails;

    public SocialUserDetailsImpl(UserDetailsImpl userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetailsImpl getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsImpl userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public String getUserId() {
        return this.userDetails.getId() + "";
    }

    @Override
    public String getUsername() {
        return userDetails.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public String getPassword() {
        return userDetails.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}