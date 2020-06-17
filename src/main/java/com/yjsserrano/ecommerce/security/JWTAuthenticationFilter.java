package com.yjsserrano.ecommerce.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjsserrano.ecommerce.EcommerceApplication;
import com.yjsserrano.ecommerce.domain.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.yjsserrano.ecommerce.security.SecurityConstants.*;


/**
 * The {@link JWTAuthenticationFilter} is responsible for the authentication process  of the Serrano {@link EcommerceApplication}
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
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    /**
     * {@link JWTAuthenticationFilter} Constructor
     *
     * @param authenticationManager {@link AuthenticationManager}
     */
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    /**
     * Here it's verified the credentials of the {@link User} to verify if can access the resources
     * provided by the API
     *
     * @param req {@link HttpServletRequest}
     * @param res {@link HttpServletResponse}
     * @return {@link Authentication}
     * @throws AuthenticationException if some problem occur in the {@link Authentication} process
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User userCredential = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userCredential.getUsername(),
                            userCredential.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * After the {@link Authentication} here it's returned the Bearer with the code to be used to use the resources
     * of the API.
     *
     * @param req   {@link HttpServletRequest}
     * @param res   {@link HttpServletResponse}
     * @param chain {@link FilterChain}
     * @param auth  {@link Authentication}
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }


    /**
     * After the {@link Authentication} fails, it's throw a message showing this failure
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param failed   {@link AuthenticationException}
     * @throws IOException      Problem with input and output operation
     * @throws ServletException Problem with the servlet handling the process of  {@link Authentication}
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
