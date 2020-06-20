package com.yjsserrano.ecommerce;

import com.yjsserrano.ecommerce.controller.UserController;
import com.yjsserrano.ecommerce.domain.Cart;
import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.dto.UserDTO;
import com.yjsserrano.ecommerce.repository.CartRepository;
import com.yjsserrano.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Here I will be testing all the endpoints and main features of the {@link UserController}
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
public class UserControllerTest {
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);
    private UserController userController;

    /**
     * Before each method we instantiate the {@link UserController} and we mock the private fields:
     * <p>
     * 1. {@link CartRepository}
     * 2. {@link UserRepository}
     * 3. {@link BCryptPasswordEncoder}
     */
    @BeforeEach
    public void initMethod() {
        userController = new UserController();
        MockField.injectPrivateField(userController, "cartRepository", cartRepository);
        MockField.injectPrivateField(userController, "userRepository", userRepository);
        MockField.injectPrivateField(userController, "passwordEncoder", passwordEncoder);
    }

    /**
     * This test checks whether the {@link User} was created successfully
     */
    @DisplayName("Testing - Create user (Success)")
    @Order(1)
    @Test
    public void testCreateUserSuccess() {
        when(passwordEncoder.encode("Serrano")).thenReturn("S3rr@n0");

        UserDTO userDTO = getUserDTO();

        final ResponseEntity<User> userResponseEntity = userController.createUser(userDTO);
        assertThat(userResponseEntity, notNullValue());
        assertThat(userResponseEntity.getStatusCodeValue(), is(equalTo(200)));

        User user = userResponseEntity.getBody();
        assertThat(user, notNullValue());
        assertThat(user.getId(), is(equalTo(0L)));
        assertThat(user.getUsername(), is(equalTo(userDTO.getUsername())));
        assertThat(user.getPassword(), is(equalTo("S3rr@n0")));
    }

    /**
     * This test checks whether the {@link User} can not be created, because the username already exist into the database
     */
    @DisplayName("Testing - Create user (Failure - Username Already Exist)")
    @Order(2)
    @Test
    public void testCreateUserUsernameAlreadyExistFailure() {
        User user = getUser();
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        UserDTO userDTO = getUserDTO();

        final ResponseEntity<User> userResponseEntity = userController.createUser(userDTO);
        assertThat(userResponseEntity, notNullValue());
        assertThat(userResponseEntity.getStatusCodeValue(), is(equalTo(400)));
    }

    /**
     * This test checks whether the {@link User} can not be created, because the password and confirm password
     * does not match
     */
    @DisplayName("Testing - Create user (Failure - Password & Confirm Password Not Match )")
    @Order(3)
    @Test
    public void testCreateUserPasswordNotMatchFailure() {
        UserDTO userDTO = getUserDTO();
        userDTO.setConfirmPassword("Beatriz");

        final ResponseEntity<User> userResponseEntity = userController.createUser(userDTO);
        assertThat(userResponseEntity, notNullValue());
        assertThat(userResponseEntity.getStatusCodeValue(), is(equalTo(400)));
    }

    /**
     * This test checks whether the {@link User} can not be created, because the password requirement was not followed
     */
    @DisplayName("Testing - Create user (Failure - Password Requirement Not Followed )")
    @Order(4)
    @Test
    public void testCreateUserPasswordRequirementFailure() {
        UserDTO userDTO = getUserDTO();
        userDTO.setPassword("Yuri");
        userDTO.setConfirmPassword("Yuri");

        final ResponseEntity<User> userResponseEntity = userController.createUser(userDTO);
        assertThat(userResponseEntity, notNullValue());
        assertThat(userResponseEntity.getStatusCodeValue(), is(equalTo(400)));
    }

    /**
     * This test checks whether the {@link User} was retrieved by your id successfully
     */
    @DisplayName("Testing - Getting user by your id (Success)")
    @Order(5)
    @Test
    public void testFindUserByIdSuccess() {
        User user = getUser();
        Cart cart = getCart();
        user.setCart(cart);
        when(userRepository.findById(0L)).thenReturn(Optional.of(user));

        final ResponseEntity<User> userResponseEntity = userController.findUserById(0L);
        assertThat(userResponseEntity, notNullValue());
        assertThat(userResponseEntity.getStatusCodeValue(), is(equalTo(200)));

        User userRetrieved = userResponseEntity.getBody();
        assertThat(userRetrieved, notNullValue());
        assertThat(userRetrieved.getId(), is(equalTo(user.getId())));
        assertThat(userRetrieved.getUsername(), is(equalTo(user.getUsername())));
        assertThat(userRetrieved.getPassword(), is(equalTo(user.getPassword())));
    }

    /**
     * This test checks whether the {@link User} was not retrieved, because your id was not found
     */
    @DisplayName("Testing - Getting user by your id (Failure - Id Not Found)")
    @Order(6)
    @Test
    public void testFindUserByIdNotFoundFailure() {
        final ResponseEntity<User> userResponseEntity = userController.findUserById(99L);
        assertThat(userResponseEntity, notNullValue());
        assertThat(userResponseEntity.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * This test checks whether the {@link User} was retrieved by your username successfully
     */
    @DisplayName("Testing - Getting user by your username (Success)")
    @Order(7)
    @Test
    public void testFindUserByUsernameSuccess() {
        User user = getUser();
        Cart cart = getCart();
        user.setCart(cart);
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        final ResponseEntity<User> userResponseEntity = userController.findUserByUsername(user.getUsername());
        assertThat(userResponseEntity, notNullValue());
        assertThat(userResponseEntity.getStatusCodeValue(), is(equalTo(200)));

        User userRetrieved = userResponseEntity.getBody();
        assertThat(userRetrieved, notNullValue());
        assertThat(userRetrieved.getId(), is(equalTo(user.getId())));
        assertThat(userRetrieved.getUsername(), is(equalTo(user.getUsername())));
        assertThat(userRetrieved.getPassword(), is(equalTo(user.getPassword())));
    }

    /**
     * This test checks whether the {@link User} was not retrieved, because your username was not found
     */
    @DisplayName("Testing - Getting user by your username (Failure - Username Not Found)")
    @Order(8)
    @Test
    public void testFindUserByUsernameNotFoundFailure() {
        final ResponseEntity<User> userResponseEntity = userController.findUserByUsername("Beatriz");
        assertThat(userResponseEntity, notNullValue());
        assertThat(userResponseEntity.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * Here we are returning a pre-configured {@link User} to be used in the tests
     *
     * @return {@link User}
     */
    private User getUser() {
        return User.builder().id(0L).username("Yuri").password("Serrano").build();
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
     * Here we are returning a pre-configured {@link UserDTO} to be used in the tests
     *
     * @return {@link UserDTO}
     */
    private UserDTO getUserDTO() {
        return UserDTO.builder().username("Yuri").password("Serrano").confirmPassword("Serrano").build();
    }
}
