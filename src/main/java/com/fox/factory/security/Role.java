package com.fox.factory.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum Role {
    USER(Set.of(Permission.MANAGE_OWN_RESOURCES)),
    MODERATOR(Set.of(Permission.MANAGE_OWN_RESOURCES, Permission.MANAGE_ALL_RESOURCES, Permission.ADD_PRODUCT)),
    ADMIN(Set.of(
            Permission.MANAGE_OWN_RESOURCES, Permission.MANAGE_ALL_RESOURCES, Permission.MANAGE_APPLICATION,
            Permission.MANAGE_USERS, Permission.GET_SECURED_INFO, Permission.ADD_PRODUCT)
    ),
    DEVELOPER(Set.of(
            Permission.MANAGE_OWN_RESOURCES, Permission.MANAGE_ALL_RESOURCES, Permission.MANAGE_APPLICATION,
            Permission.MANAGE_USERS, Permission.GET_SECURED_INFO, Permission.ADD_PRODUCT));


    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toUnmodifiableSet());
    }
}
