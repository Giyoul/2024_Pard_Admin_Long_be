package com.pard.admlong_be.global.security;

import com.pard.admlong_be.domain.user.dto.request.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final UserRequestDTO.Jwt userDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add(new GrantedAuthority() {
//
//            @Override
//            public String getAuthority() {
//                return String.valueOf(userDTO.getRole());
//            }
//        });
        return collection;
    }

    @Override
    public String getPassword() {

        return "1234";
    }
    @Override
    public String getUsername() {

        return userDTO.getEmail();
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
