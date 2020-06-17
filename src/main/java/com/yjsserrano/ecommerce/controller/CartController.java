package com.yjsserrano.ecommerce.controller;

import com.yjsserrano.ecommerce.domain.Cart;
import com.yjsserrano.ecommerce.domain.Item;
import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.dto.CartDTO;
import com.yjsserrano.ecommerce.repository.CartRepository;
import com.yjsserrano.ecommerce.repository.ItemRepository;
import com.yjsserrano.ecommerce.repository.UserRepository;
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
 * @since 1.0
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Item> item = itemRepository.findById(cartDTO.getItemId());
        if (!item.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cart cart = user.getCart();
        IntStream.range(0, cartDTO.getQuantity())
                .forEach(i -> cart.addItem(item.get()));
        cartRepository.save(cart);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Item> item = itemRepository.findById(cartDTO.getItemId());
        if (!item.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cart cart = user.getCart();
        IntStream.range(0, cartDTO.getQuantity())
                .forEach(i -> cart.removeItem(item.get()));
        cartRepository.save(cart);
        return ResponseEntity.ok(cart);
    }
}
