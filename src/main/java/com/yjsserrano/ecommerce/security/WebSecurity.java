package com.yjsserrano.ecommerce.security;

import com.yjsserrano.ecommerce.EcommerceApplication;
import com.yjsserrano.ecommerce.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.yjsserrano.ecommerce.security.SecurityConstants.SIGN_UP_URL;

/**
 * Here it's configured the access and security of Serrano {@link EcommerceApplication}
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/">JWT I</a>
 * @see <a href="https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world">JWT II</a>
 * @see <a href="https://www.youtube.com/watch?v=soGRyl9ztjI">JWT III</a>
 * @see <a href="https://youtu.be/X80nJ5T7YpE">JWT IV</a>
 * @since 1.0
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * {@link WebSecurity} Constructor
     *
     * @param userDetailsService    {@link UserDetailsServiceImpl}
     * @param bCryptPasswordEncoder {@link BCryptPasswordEncoder}
     */
    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Here it's defined which resources are public and which are secured
     *
     * @param http {@link HttpSecurity}
     * @throws Exception if somethings goes wrong with the security process
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Here it's loaded {@link User} specific data, setting the encryption method used by Serrano {@link EcommerceApplication}
     *
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception if something goes wrong with the authentication process
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Here it's configured the support for the CORS, in the Serrano {@link EcommerceApplication} it is open
     *
     * @return {@link CorsConfigurationSource}
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
