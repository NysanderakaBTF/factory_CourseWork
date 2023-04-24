package com.fox.factory.controllres.responcecodes;

import lombok.experimental.UtilityClass;
/**
 * A utility class that contains all the HTTP status codes.
 */

@UtilityClass
public class HttpStatusCode {

    public static final String OK = "200";
    public static final String CREATED = "201";
    public static final String ACCEPTED = "202";
    public static final String NO_CONTENT = "204";
    public static final String BAD_REQUEST = "400";
    public static final String UNAUTHORIZED = "401";
    public static final String FORBIDDEN = "403";
    public static final String NOT_FOUND = "404";
    public static final String METHOD_NOT_ALLOWED = "405";
    public static final String CONFLICT = "409";
    public static final String INTERNAL_SERVER_ERROR = "500";
    public static final String NOT_IMPLEMENTED = "501";
    public static final String BAD_GATEWAY = "502";
    public static final String SERVICE_UNAVAILABLE = "503";
    public static final String GATEWAY_TIMEOUT = "504";
    public static final String HTTP_VERSION_NOT_SUPPORTED = "505";
}
