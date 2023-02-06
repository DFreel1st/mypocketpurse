package com.project.mypocketpurse.service.userdetails;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class CustomUserDetails
        implements UserDetails {


    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;
    private Integer id;
    private Boolean enable;
    private Boolean accountNotExpired;
    private Boolean accountNotLocked;
    private Boolean credentialNotExpired;

    public CustomUserDetails(final Integer id,
                             final String username,
                             final String password,
                             final Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountNotExpired = true;
        this.accountNotLocked = true;
        this.credentialNotExpired = true;
        this.enable = true;
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
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    public Integer getUserId() {
        return id;
    }
}
