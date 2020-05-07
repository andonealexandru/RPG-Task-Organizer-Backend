package com.backend.rpgtask.shared;

import com.backend.rpgtask.io.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = -863341970655888129L;

    private String email;
    private String encryptedPassword;
    private Boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String email, String encryptedPassword, Boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static UserPrincipal build(UserEntity userEntity) {
        List<GrantedAuthority> authorityList = userEntity.getRoles().stream().map(roleEntity ->
                new SimpleGrantedAuthority(roleEntity.getName().name()))
                .collect(Collectors.toList());

        return new UserPrincipal(
                userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                userEntity.getEnabled(),
                authorityList
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public String getUsername() {
        return email;
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
