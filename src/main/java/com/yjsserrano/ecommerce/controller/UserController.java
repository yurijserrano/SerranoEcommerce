package com.yjsserrano.ecommerce.controller;

import com.yjsserrano.ecommerce.domain.Cart;
import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.dto.UserDTO;
import com.yjsserrano.ecommerce.repository.CartRepository;
import com.yjsserrano.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;

/**
 * Rest Controller {@link User} endpoint
 * <p>
 * Handles web requests related to {@link User}
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://www.baeldung.com/spring-request-response-body">@ResponseBody</a>
 * @see <a href="https://www.baeldung.com/spring-new-requestmapping-shortcuts">@RequestMapping</a>
 * @see <a href="https://howtodoinjava.com/spring5/webmvc/controller-getmapping-postmapping/">@PostMapping & GetMapping I</a>
 * @see <a href="http://zetcode.com/spring/postmapping/">@PostMapping & GetMapping II</a>
 * @since 1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * Verify if the {@link User} password has at least 7 characters
     *
     * @param password {@link String}
     * @return {@link Boolean}
     */
    private boolean checkUserPassword(String password) {
        return password.length() >= 7;
    }

    /**
     * Here it's checked if the {@link User} can be created based on the items below:
     * <p>
     * 1 - Password must have more than 6 characters
     * 2 - The password and the confirmation of the password must be identical
     * 3 - The {@link User} must not exist in the database
     *
     * @param userDTO {@link UserDTO}
     * @return {@link Boolean}
     */
    private boolean checkUserDTO(UserDTO userDTO) {
        return checkUserPassword(userDTO.getPassword())
                && userDTO.getPassword().equals(userDTO.getConfirmPassword())
                && isNull(userRepository.findUserByUsername(userDTO.getUsername()));
    }

    /**
     * Here the {@link User} it's returned by your id
     *
     * @param id {@link Long}
     * @return {@link ResponseEntity<User>}
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        return ResponseEntity.of(userRepository.findById(id));
    }

    /**
     * Here the {@link User} it's returned by your username
     *
     * @param username {@link String}
     * @return {@link ResponseEntity<User>}
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        User user = userRepository.findUserByUsername(username);
        return isNull(user) ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    /**
     * Here it's where the {@link User} is created
     *
     * @param userDTO {@link UserDTO}
     * @return {@link ResponseEntity<User>}
     */
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        if (!checkUserDTO(userDTO)) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
