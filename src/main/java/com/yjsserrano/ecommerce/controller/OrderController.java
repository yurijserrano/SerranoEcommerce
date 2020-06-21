package com.yjsserrano.ecommerce.controller;

import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.domain.UserOrder;
import com.yjsserrano.ecommerce.repository.OrderRepository;
import com.yjsserrano.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @see <a href="https://lankydan.dev/2019/01/09/configuring-logback-with-spring-boot">Logback I</a>
 * @see <a href="https://www.javaguides.net/2018/09/spring-boot-2-logging-slf4j-logback-and-log4j-example.html">Logback II</a>
 * @see <a href="https://dzone.com/articles/configuring-logback-with-spring-boot">Logback III</a>
 * @see <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging">Logback IV</a>
 * @see <a href="https://examples.javacodegeeks.com/enterprise-java/logback/logback-configuration-example/">Logback V</a>
 * @since 1.0
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger("splunk.logger");

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
            log.error("Method: submitUserOrder | Status: Error | Message: User {} was not found in the database", username);
            return ResponseEntity.notFound().build();
        }
        UserOrder order = UserOrder.createFromCart(user.getCart());
        orderRepository.save(order);
        log.info("Method: submitUserOrder | Status: Success | Message: The order of the user {} " +
                "was sent successfully", username);
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
            log.error("Method: getOrdersForUser | Status: Error | Message: User {} was not found in the database, so " +
                    "the orders can not be retrieved", username);
            return ResponseEntity.notFound().build();
        }
        log.info("Method: getOrdersForUser | Status: Success | Message: The orders of the user {} " +
                "were retrieved successfully", username);
        return ResponseEntity.ok(orderRepository.findUserOrdersByUser(user));
    }
}
