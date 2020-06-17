package com.yjsserrano.ecommerce.controller;

import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.domain.UserOrder;
import com.yjsserrano.ecommerce.repository.OrderRepository;
import com.yjsserrano.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * Rest Controller {@link UserOrder} endpoint
 * <p>
 * Handles web requests related to {@link UserOrder} of the {@link User}
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
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    /**
     * Here it's saved the order of the {@link User}
     *
     * @return {@link ResponseEntity<UserOrder>}
     */
    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> submitUserOrder(@PathVariable String username) {
        User user = userRepository.findUserByUsername(username);
        if (isNull(user)) {
            return ResponseEntity.notFound().build();
        }
        UserOrder order = UserOrder.createFromCart(user.getCart());
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    /**
     * Here it's returned the history of {@link User} orders
     *
     * @param username {@link String}
     * @return {@link List<UserOrder>}
     */
    @GetMapping("/history/{username}")
    public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
        User user = userRepository.findUserByUsername(username);
        if (isNull(user)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderRepository.findUserOrdersByUser(user));
    }
}
