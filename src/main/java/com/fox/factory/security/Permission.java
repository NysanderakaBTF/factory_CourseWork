package com.fox.factory.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.cfg.internal.AbstractDomainDataCachingConfig;

@Getter
@RequiredArgsConstructor
public enum Permission {

    MANAGE_OWN_RESOURCES("manage_own_resources"),
    MANAGE_ALL_RESOURCES("manage_all_resources"),
    MANAGE_APPLICATION("manage_application"),
    MANAGE_USERS("manage_users"),
    GET_SECURED_INFO("get_secured_info"),
    READ("read"),
    DELETE_COMMENTS("delete_comments"),
    WRITE("write"),

    ADD_PRODUCT("add_product");



    private final String permission;

}
