package com.itechnews.security;

import com.itechnews.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    //or extends org.springframework.security.core.userdetails.User;
    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;
    private Boolean active;
    private Role role;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(String userName, String password,
                           Boolean active, Role role, List<GrantedAuthority> authorities) {
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.role = role;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
