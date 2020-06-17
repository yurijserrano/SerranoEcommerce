package com.yjsserrano.ecommerce.security;

/**
 * The {@link SecurityConstants} contains the constants that will be used with the JWT process
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/">JWT I</a>
 * @see <a href="https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world">JWT II</a>
 * @see <a href="https://www.youtube.com/watch?v=soGRyl9ztjI">JWT III</a>
 * @see <a href="https://youtu.be/X80nJ5T7YpE">JWT IV</a>
 * @see <a href="https://www.geeksforgeeks.org/final-keyword-java/">Final Keyword I</a>
 * @see <a href="https://www.educative.io/edpresso/what-is-the-final-keyword-in-java">Final Keyword II</a>
 * @see <a href="https://www.baeldung.com/java-final">Final Keyword III</a>
 * @see <a href="https://www.javatpoint.com/final-keyword">Final Keyword IV</a>
 * @since 1.0
 */
public class SecurityConstants {
    public static final String SECRET = "serrano";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/create";
}

