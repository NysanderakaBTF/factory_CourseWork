package com.fox.factory.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    MANAGE_OWN_RESOURCES("manage_own_resources"),
    MANAGE_ALL_RESOURCES("manage_all_resources"),
    MANAGE_APPLICATION("manage_application"),
    MANAGE_USERS("manage_users"),
    GET_SECURED_INFO("get_secured_info");


    private final String permission;

}
