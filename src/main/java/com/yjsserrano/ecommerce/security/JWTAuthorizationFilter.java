package com.yjsserrano.ecommerce.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yjsserrano.ecommerce.EcommerceApplication;
import com.yjsserrano.ecommerce.domain.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.yjsserrano.ecommerce.security.SecurityConstants.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * The {@link JWTAuthorizationFilter} is responsible for the authorization process  of the Serrano {@link EcommerceApplication}
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/">JWT I</a>
 * @see <a href="https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world">JWT II</a>
 * @see <a href="https://www.youtube.com/watch?v=soGRyl9ztjI">JWT III</a>
 * @see <a href="https://youtu.be/X80nJ5T7YpE">JWT IV</a>
 * @since 1.0
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    /**
     * {@link JWTAuthorizationFilter} Constructor
     *
     * @param authenticationManager {@link AuthenticationManager}
     */
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * Here the requests are filtered, checking if the {@link User} can access the resources of the API
     *
     * @param req   {@link HttpServletRequest}
     * @param res   {@link HttpServletResponse}
     * @param chain {@link FilterChain}
     * @throws IOException      Problem with input and output operation
     * @throws ServletException Problem with the servlet handling the process of Authorization
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (isNull(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    /**
     * Here it's made the authentication of the token and it's returned {@link UsernamePasswordAuthenticationToken}
     *
     * @param request {@link HttpServletRequest}
     * @return {@link UsernamePasswordAuthenticationToken}
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (nonNull(token)) {
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (nonNull(user)) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
