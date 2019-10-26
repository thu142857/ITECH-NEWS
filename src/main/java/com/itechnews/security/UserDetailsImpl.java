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
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String userName;
    private String displayedName;
    private String password;
    private Boolean active;
    private Role role;
    private String image;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String userName, String displayedName, String password, String image,
                           Boolean active, Role role, List<GrantedAuthority> authorities) {
        this.id = id;
        this.userName = userName;
        this.displayedName = displayedName;
        this.password = password;
        this.image = image;
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
