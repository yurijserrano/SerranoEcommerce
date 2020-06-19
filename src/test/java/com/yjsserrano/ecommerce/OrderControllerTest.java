package com.yjsserrano.ecommerce;

import com.yjsserrano.ecommerce.controller.OrderController;
import com.yjsserrano.ecommerce.domain.Cart;
import com.yjsserrano.ecommerce.domain.Item;
import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.domain.UserOrder;
import com.yjsserrano.ecommerce.repository.OrderRepository;
import com.yjsserrano.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Here I will be testing all the endpoints and main features of the {@link OrderController}
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
public class OrderControllerTest {
    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private OrderController orderController;

    /**
     * Before each method we instantiate the {@link OrderController} and we mock the private fields:
     * <p>
     * 1. {@link OrderRepository}
     * 2. {@link UserRepository}
     */
    @BeforeEach
    public void initMethod() {
        orderController = new OrderController();
        MockField.injectPrivateField(orderController, "orderRepository", orderRepository);
        MockField.injectPrivateField(orderController, "userRepository", userRepository);
    }

    /**
     * This test checks whether the {@link User} {@link Order} was sent successfully
     */
    @DisplayName("Testing - Submit user order (Success)")
    @Order(1)
    @Test
    public void testSubmitUserOrderSuccess() {
        User user = getUser();
        Cart cart = getCart();
        Item item = getItem();
        cart.setUser(user);
        cart.setTotal(new BigDecimal("1000.00"));
        user.setCart(cart);

        user.getCart().addItem(item);
        user.getCart().addItem(item);
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        final ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submitUserOrder(user.getUsername());
        assertThat(userOrderResponseEntity, notNullValue());
        assertThat(userOrderResponseEntity.getStatusCodeValue(), is(equalTo(200)));

        UserOrder userOrder = userOrderResponseEntity.getBody();
        assertThat(userOrder, notNullValue());
        assertThat(userOrder.getUser(), notNullValue());
        assertThat(userOrder.getUser().getId(), is(equalTo(user.getId())));
        assertThat(userOrder.getUser().getUsername(), is(equalTo(user.getUsername())));
        assertThat(userOrder.getUser().getPassword(), is(equalTo(user.getPassword())));
        assertThat(userOrder.getItems(), notNullValue());
        assertThat(userOrder.getItems().size(), is(equalTo(cart.getItems().size())));
        assertThat(userOrder.getTotal(), notNullValue());
        assertThat(userOrder.getTotal(), is(equalTo(cart.getTotal())));
    }

    /**
     * This test checks whether the {@link User} {@link Order} was not sent successfully, because the username was not found
     */
    @DisplayName("Testing - Submit user order (Failure - Username Not Found)")
    @Order(2)
    @Test
    public void testSubmitUserOrderUsernameFailure() {
        final ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submitUserOrder("Beatriz");
        assertThat(userOrderResponseEntity, notNullValue());
        assertThat(userOrderResponseEntity.getStatusCodeValue(), is(equalTo(404)));
    }


    /**
     * This test checks whether the {@link User} {@link Order} was retrieved successfully
     */
    @DisplayName("Testing - Getting user order (Success)")
    @Order(3)
    @Test
    public void testGetOrdersForUserSuccess() {
        User user = getUser();
        Cart cart = getCart();
        Item item = getItem();
        cart.setUser(user);
        cart.setTotal(new BigDecimal("1000.00"));
        user.setCart(cart);

        user.getCart().addItem(item);
        user.getCart().addItem(item);

        UserOrder userOrder = UserOrder.createFromCart(user.getCart());
        userOrder.setId(0L);


        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);
        when(orderRepository.findUserOrdersByUser(user)).thenReturn(Collections.singletonList(userOrder));

        final ResponseEntity<List<UserOrder>> userOrderResponseEntity = orderController.getOrdersForUser(user.getUsername());
        assertThat(userOrderResponseEntity, notNullValue());
        assertThat(userOrderResponseEntity.getStatusCodeValue(), is(equalTo(200)));

        List<UserOrder> userOrderList = userOrderResponseEntity.getBody();
        assertThat(userOrderList, notNullValue());
        assertThat(userOrderList.isEmpty(), is(false));
        assertThat(userOrderList.get(0).getId(), is(equalTo(userOrder.getId())));
        assertThat(userOrderList.get(0).getTotal(), notNullValue());
        assertThat(userOrderList.get(0).getTotal(), is(equalTo(userOrder.getTotal())));
        assertThat(userOrderList.get(0).getItems(), notNullValue());
        assertThat(userOrderList.get(0).getItems().size(), is(equalTo(userOrder.getItems().size())));
        assertThat(userOrderList.get(0).getUser(), notNullValue());
        assertThat(userOrderList.get(0).getUser().getId(), is(equalTo(userOrder.getUser().getId())));
        assertThat(userOrderList.get(0).getUser().getUsername(), is(equalTo(userOrder.getUser().getUsername())));
        assertThat(userOrderList.get(0).getUser().getPassword(), is(equalTo(userOrder.getUser().getPassword())));
    }

    /**
     * This test checks whether the {@link User} {@link Order} was not retrieved successfully, because the username was not found
     */
    @DisplayName("Testing - Getting user order (Failure - Username Not Found)")
    @Order(4)
    @Test
    public void testGetOrdersForUserUsernameFailure() {
        final ResponseEntity<List<UserOrder>> userOrderResponseEntity = orderController.getOrdersForUser("Beatriz");
        assertThat(userOrderResponseEntity, notNullValue());
        assertThat(userOrderResponseEntity.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * Here we are returning a pre-configured {@link User} to be used in the tests
     *
     * @return {@link User}
     */
    private User getUser() {
        return User.builder().id(0).username("Yuri").password("Serrano").build();
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
     * Here we are returning a pre-configured {@link Item} to be used in the tests
     *
     * @return {@link Item}
     */
    private Item getItem() {
        return Item.builder().id((long) 0)
                .name("Iphone X")
                .description("The best Iphone of the market")
                .price(new BigDecimal("500.00"))
                .build();
    }
}
