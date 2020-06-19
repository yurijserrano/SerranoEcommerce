package com.yjsserrano.ecommerce;

import com.yjsserrano.ecommerce.controller.CartController;
import com.yjsserrano.ecommerce.domain.Cart;
import com.yjsserrano.ecommerce.domain.Item;
import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.dto.CartDTO;
import com.yjsserrano.ecommerce.repository.CartRepository;
import com.yjsserrano.ecommerce.repository.ItemRepository;
import com.yjsserrano.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Here I will be testing all the endpoints and main features of the {@link CartController}
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="http://hamcrest.org/JavaHamcrest/tutorial">AssertThat & Hamcrest I</a>
 * @see <a href="https://www.baeldung.com/java-junit-hamcrest-guide">AssertThat & Hamcrest II</a>
 * @see <a href="https://www.vogella.com/tutorials/Hamcrest/article.html">AssertThat & Hamcrest III</a>
 * @see <a href="https://crunchify.com/how-to-use-hamcrest-assertthat-matchers-to-create-junit-testcases-in-java-complete-tutorial/">AssertThat & Hamcrest IV</a>
 * @see <a href="https://springframework.guru/unit-testing-junit-part-3-hamcrest-matchers/">AssertThat & Hamcrest V</a>
 * @see <a href="https://dzone.com/articles/the-benefit-of-using-assertthat-over-other-assert">AssertThat & Hamcrest VI</a>
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations">Test Annotations I</a>
 * @see <a href="https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall">Test Annotations II</a>
 * @see <a href="https://youtu.be/eJzTQDqL3rc">Mockito I</a>
 * @see <a href="https://youtu.be/kXhYu939_5s">Mockito II</a>
 * @see <a href="https://youtu.be/e6GAfk3ACVw">Mockito III</a>
 * @see <a href="https://www.springboottutorial.com/spring-boot-unit-testing-and-mocking-with-mockito-and-junit">Mockito IV</a>
 * @see <a href="https://www.springboottutorial.com/spring-boot-unit-testing-and-mocking-with-mockito-and-junit">Mockito IV</a>
 * @see <a href="https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockito-junit-example/">Mockito V</a>
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartControllerTest {
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private CartController cartController;

    /**
     * Before each method we instantiate the {@link CartController} and we mock the private fields:
     * <p>
     * 1. {@link CartRepository}
     * 2. {@link UserRepository}
     * 3. {@link ItemRepository}
     */
    @BeforeEach
    public void initMethod() {
        cartController = new CartController();
        MockField.injectPrivateField(cartController, "cartRepository", cartRepository);
        MockField.injectPrivateField(cartController, "userRepository", userRepository);
        MockField.injectPrivateField(cartController, "itemRepository", itemRepository);
    }


    /**
     * This test checks if the {@link Item} it's being added successfully into the {@link Cart}
     */
    @DisplayName("Testing - Adding item to the cart (Success)")
    @Order(1)
    @Test
    public void testAddToCartSuccess() {
        User user = getUser();
        when(userRepository.findUserByUsername("Yuri")).thenReturn(user);

        Cart cart = getCart();
        cart.setUser(user);
        user.setCart(cart);

        Item item01 = getItem(0L,
                "Iphone X",
                "The best Iphone of the market",
                "500.00");
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item01));

        cart.addItem(item01);

        Item item02 = getItem(1L,
                "ASUS ROG Phone II",
                "The best Android smartphone",
                "450.00");
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item02));

        CartDTO cartDTO = getCartDTO(item02.getId(), user.getUsername(), 2);

        final ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(cartDTO);
        Cart responseBody = cartResponseEntity.getBody();
        assertThat(cartResponseEntity, notNullValue());
        assertThat(cartResponseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(responseBody, notNullValue());
        assertThat(responseBody.getId(), is(equalTo(cart.getId())));
        assertThat(responseBody.getUser().getId(), is(equalTo(cart.getUser().getId())));
        assertThat(responseBody.getItems().size(), is(equalTo(cart.getItems().size())));
        assertThat(responseBody.getTotal(), is(equalTo(cart.getTotal())));
    }

    /**
     * This tests check if the {@link Item} can not be added to the {@link Cart} because the {@link User} was not found
     */
    @DisplayName("Testing - Adding item to the cart (Failure - Username Not Found)")
    @Order(2)
    @Test
    public void testAddToCartUsernameFailure() {
        when(userRepository.findUserByUsername("Beatriz")).thenReturn(null);

        CartDTO cartDTO = getCartDTO();

        final ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(cartDTO);
        assertThat(cartResponseEntity, notNullValue());
        assertThat(cartResponseEntity.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * This tests check if the {@link Item} can not be added to the {@link Cart} because the {@link User} was not found
     */
    @DisplayName("Testing - Adding item to the cart (Failure - Item Not Found)")
    @Order(3)
    @Test
    public void testAddToCartItemFailure() {
        User user = getUser();
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        when(itemRepository.findById(0L)).thenReturn(Optional.empty());

        CartDTO cartDTO = getCartDTO(0L, user.getUsername(), 1);

        final ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(cartDTO);
        assertThat(cartResponseEntity, notNullValue());
        assertThat(cartResponseEntity.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * This tests check if the {@link Item} can be removed from {@link Cart} with success
     */
    @DisplayName("Testing - Removing item from the cart (Success)")
    @Order(4)
    @Test
    public void testRemoveFromCartSuccess() {
        User user = getUser();
        when(userRepository.findUserByUsername("Yuri")).thenReturn(user);

        Cart cart = getCart();
        cart.setUser(user);
        user.setCart(cart);

        Item item01 = getItem(0L,
                "Iphone X",
                "The best Iphone of the market",
                "500.00");
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item01));

        cart.addItem(item01);

        Item item02 = getItem(1L,
                "ASUS ROG Phone II",
                "The best Android smartphone",
                "450.00");
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item02));

        CartDTO cartDTO = getCartDTO(item02.getId(), user.getUsername(), 2);

        final ResponseEntity<Cart> cartResponseEntity = cartController.removeFromCart(cartDTO);
        Cart responseBody = cartResponseEntity.getBody();
        assertThat(cartResponseEntity, notNullValue());
        assertThat(cartResponseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(responseBody, notNullValue());
        assertThat(responseBody.getId(), is(equalTo(cart.getId())));
        assertThat(responseBody.getUser().getId(), is(equalTo(cart.getUser().getId())));
        assertThat(responseBody.getItems().size(), is(equalTo(cart.getItems().size())));
        assertThat(responseBody.getTotal(), is(equalTo(cart.getTotal())));
    }

    /**
     * This tests check if the {@link Item} can not be removed from the {@link Cart} because the {@link User} was not found
     */
    @DisplayName("Testing - Removing item from the cart (Failure - Username Not Found)")
    @Order(5)
    @Test
    public void testRemoveFromCartUsernameFailure() {
        when(userRepository.findUserByUsername("Beatriz")).thenReturn(null);

        CartDTO cartDTO = getCartDTO();

        final ResponseEntity<Cart> cartResponseEntity = cartController.removeFromCart(cartDTO);
        assertThat(cartResponseEntity, notNullValue());
        assertThat(cartResponseEntity.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * This tests check if the {@link Item} can not be removed from the {@link Cart} because the {@link Item} was not found
     */
    @DisplayName("Testing - Removing item from the cart (Failure - Item Not Found)")
    @Order(6)
    @Test
    public void testRemoveFromCartItemFailure() {
        User user = getUser();
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        when(itemRepository.findById(0L)).thenReturn(Optional.empty());

        CartDTO cartDTO = getCartDTO(0L, user.getUsername(), 1);

        final ResponseEntity<Cart> cartResponseEntity = cartController.removeFromCart(cartDTO);
        assertThat(cartResponseEntity, notNullValue());
        assertThat(cartResponseEntity.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * Here we are returning a pre-configured {@link User} to be used in the tests
     *
     * @return {@link User}
     */
    private User getUser() {
        return User.builder().username("Yuri").id(0L).password("Serrano").build();
    }

    /**
     * Here we are returning a pre-configured {@link Cart} to be used in the tests
     *
     * @return {@link Cart}
     */
    private Cart getCart() {
        return Cart.builder().id(0L).build();
    }

    /**
     * Here we are returning a pre-configured {@link CartDTO} to be used in the tests
     *
     * @return {@link CartDTO}
     */
    private CartDTO getCartDTO() {
        return CartDTO.builder().username("Beatriz").build();
    }

    /**
     * Here we are returning a pre-configured {@link CartDTO} to be used in the tests
     *
     * @return {@link CartDTO}
     */
    private CartDTO getCartDTO(long itemId, String itemOwner, int itemQuantity) {
        return CartDTO.builder().username(itemOwner).itemId(itemId).quantity(itemQuantity).build();
    }

    /**
     * Here we are returning a pre-configured {@link Item} to be used in the tests
     *
     * @return {@link Item}
     */
    private Item getItem(long itemId, String itemName, String itemDescription, String itemPrice) {
        return Item.builder().id(itemId).name(itemName).description(itemDescription)
                .price(new BigDecimal(itemPrice))
                .build();
    }
}
