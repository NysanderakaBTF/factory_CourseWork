package com.fox.factory.security;

import com.fox.factory.entities.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SecurityUser implements UserDetails {
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String email;

    private List<SimpleGrantedAuthority> authorities;
    private boolean isActive;

    public static UserDetails fromUser(User user) {
        return SecurityUser.builder()
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
//                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().name())))
                .authorities(user.getRole().getAuthorities().stream().toList())
                .isActive(user.getStatus().equals(Status.ACTIVE))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}

