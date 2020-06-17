package com.yjsserrano.ecommerce.security;


import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

/**
 * Here contains all the business logic related to the retrieval of the {@link User}
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/">JWT I</a>
 * @see <a href="https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world">JWT II</a>
 * @see <a href="https://www.youtube.com/watch?v=soGRyl9ztjI">JWT III</a>
 * @see <a href="https://youtu.be/X80nJ5T7YpE">JWT IV</a>
 * @see <a href="https://www.journaldev.com/21435/spring-service-annotation">Service I</a>
 * @see <a href="https://www.baeldung.com/spring-component-repository-service">Service II</a>
 * @since 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Here it's retrieved the {@link User} using your username
     *
     * @param username {@link String}
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException throws if the {@link User} it's not found using the username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (isNull(user)) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
    }
}
