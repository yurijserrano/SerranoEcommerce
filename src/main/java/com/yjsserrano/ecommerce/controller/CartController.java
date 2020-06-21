package com.yjsserrano.ecommerce.controller;

import com.yjsserrano.ecommerce.domain.Cart;
import com.yjsserrano.ecommerce.domain.Item;
import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.dto.CartDTO;
import com.yjsserrano.ecommerce.repository.CartRepository;
import com.yjsserrano.ecommerce.repository.ItemRepository;
import com.yjsserrano.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;


/**
 * Rest Controller {@link Cart} endpoint
 * <p>
 * Handles web requests related to {@link Cart} of the {@link User}
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://www.baeldung.com/spring-request-response-body">@ResponseBody</a>
 * @see <a href="https://www.baeldung.com/spring-new-requestmapping-shortcuts">@RequestMapping</a>
 * @see <a href="https://howtodoinjava.com/spring5/webmvc/controller-getmapping-postmapping/">@PostMapping I</a>
 * @see <a href="http://zetcode.com/spring/postmapping/">@PostMapping II</a>
 * @see <a href="https://lankydan.dev/2019/01/09/configuring-logback-with-spring-boot">Logback I</a>
 * @see <a href="https://www.javaguides.net/2018/09/spring-boot-2-logging-slf4j-logback-and-log4j-example.html">Logback II</a>
 * @see <a href="https://dzone.com/articles/configuring-logback-with-spring-boot">Logback III</a>
 * @see <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging">Logback IV</a>
 * @see <a href="https://examples.javacodegeeks.com/enterprise-java/logback/logback-configuration-example/">Logback V</a>
 * @since 1.0
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger("splunk.logger");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Here I am passing a {@link CartDTO} and saving the {@link Item} into the {@link Cart}
     *
     * @param cartDTO {@link CartDTO}
     * @return {@link ResponseEntity<Cart>}
     */
    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@Valid @RequestBody CartDTO cartDTO) {
        User user = userRepository.findUserByUsername(cartDTO.getUsername());
        if (isNull(user)) {
            log.error("Method: addToCart | Status: Error | Message: User {} was not found in the database", cartDTO.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Item> item = itemRepository.findById(cartDTO.getItemId());
        if (!item.isPresent()) {
            log.error("Method: addToCart | Status: Error | Message: Item {} was not found in the database", cartDTO.getItemId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cart cart = user.getCart();
        IntStream.range(0, cartDTO.getQuantity())
                .forEach(i -> cart.addItem(item.get()));
        cartRepository.save(cart);
        log.info("Method: addToCart | Status: Success | Message: The item {} of the user {} " +
                "was added to the cart successfully", cartDTO.getItemId(), cartDTO.getUsername());
        return ResponseEntity.ok(cart);
    }

    /**
     * Here I am passing a {@link CartDTO} and removing the {@link Item} from the {@link Cart}
     *
     * @param cartDTO {@link CartDTO}
     * @return {@link ResponseEntity<Cart>}
     */
    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromCart(@Valid @RequestBody CartDTO cartDTO) {
        User user = userRepository.findUserByUsername(cartDTO.getUsername());
        if (isNull(user)) {
            log.error("Method: removeFromCart | Status: Error | Message: User {} was not found in the database", cartDTO.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Item> item = itemRepository.findById(cartDTO.getItemId());
        if (!item.isPresent()) {
            log.error("Method: removeFromCart | Status: Error | Message: Item {} was not found in the database", cartDTO.getItemId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cart cart = user.getCart();
        IntStream.range(0, cartDTO.getQuantity())
                .forEach(i -> cart.removeItem(item.get()));
        cartRepository.save(cart);
        log.info("Method: removeFromCart | Status: Success | Message: The item {} of the user {} " +
                "was removed from the cart successfully", cartDTO.getItemId(), cartDTO.getUsername());
        return ResponseEntity.ok(cart);
    }
}
