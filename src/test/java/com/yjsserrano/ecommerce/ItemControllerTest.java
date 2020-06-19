package com.yjsserrano.ecommerce;

import com.yjsserrano.ecommerce.controller.ItemController;
import com.yjsserrano.ecommerce.domain.Item;
import com.yjsserrano.ecommerce.repository.ItemRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Here I will be testing all the endpoints and main features of the {@link ItemController}
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
public class ItemControllerTest {
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private ItemController itemController;

    /**
     * Before each method we instantiate the {@link ItemController} and we mock the private fields:
     * <p>
     * 1. {@link ItemRepository}
     */
    @BeforeEach
    public void initMethod() {
        itemController = new ItemController();
        MockField.injectPrivateField(itemController, "itemRepository", itemRepository);
    }

    /**
     * This test checks if the {@link List<Item>} can be retrieved successfully
     */
    @DisplayName("Testing - Getting list of items (Success)")
    @Order(1)
    @Test
    public void testGetItemsSuccess() {
        Item item01 = getItem(0L, "Iphone X", "The best Iphone of the market", "500.00");
        Item item02 = getItem(1L, "ASUS ROG Phone II", "The best Android smartphone", "450.00");
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item01, item02));

        final ResponseEntity<List<Item>> listResponseEntity = itemController.getItems();
        assertThat(listResponseEntity, notNullValue());
        assertThat(listResponseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(listResponseEntity.getBody(), notNullValue());
        assertThat(listResponseEntity.getBody().isEmpty(), is(false));
        assertThat(listResponseEntity.getBody().size(), is(equalTo(2)));
    }

    /**
     * This test checks if an empty {@link List<Item>} can be retrieved successfully
     */
    @DisplayName("Testing - Getting empty list of items (Success)")
    @Order(2)
    @Test
    public void testGetItemsEmptySuccess() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());

        final ResponseEntity<List<Item>> listResponseEntity = itemController.getItems();
        assertThat(listResponseEntity, notNullValue());
        assertThat(listResponseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(listResponseEntity.getBody(), notNullValue());
        assertThat(listResponseEntity.getBody().isEmpty(), is(true));
        assertThat(listResponseEntity.getBody().size(), is(equalTo(0)));
    }

    /**
     * This test checks if an {@link Item} can be retrieved by your id successfully
     */
    @DisplayName("Testing - Getting an item by your id (Success)")
    @Order(3)
    @Test
    public void testGetItemByIdSuccess() {
        Item item = getItem(0L, "Iphone X", "The best Iphone of the market", "500.00");
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));

        final ResponseEntity<Item> itemResponseEntity = itemController.getItemById(0L);
        assertThat(itemResponseEntity, notNullValue());
        assertThat(itemResponseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(itemResponseEntity.getBody(), notNullValue());
        assertThat(itemResponseEntity.getBody().getId(), is(equalTo(item.getId())));
        assertThat(itemResponseEntity.getBody().getName(), is(equalTo(item.getName())));
        assertThat(itemResponseEntity.getBody().getDescription(), is(equalTo(item.getDescription())));
        assertThat(itemResponseEntity.getBody().getPrice(), is(equalTo(item.getPrice())));
    }

    /**
     * This test checks if an {@link Item} can not be retrieved by your id because this {@link Item} does not exist
     */
    @DisplayName("Testing - Getting an item by your id (Failure - Item Not Found)")
    @Order(4)
    @Test
    public void testGetItemByIdItemFailure() {
        final ResponseEntity<Item> itemResponseEntity = itemController.getItemById(99L);
        assertThat(itemResponseEntity, notNullValue());
        assertThat(itemResponseEntity.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * This test checks if the {@link List<Item>} can be retrieved by the name of the {@link Item} successfully
     */
    @DisplayName("Testing - Getting list of items by the name of the item (Success)")
    @Order(5)
    @Test
    public void testGetItemsByNameSuccess() {
        final String itemName = "Iphone X";
        Item item01 = getItem(0L, "Iphone X", "The best Iphone of the market", "500.00");
        Item item02 = getItem(0L, "Iphone X", "The best Iphone of the market", "500.00");
        when(itemRepository.findItemsByName(itemName)).thenReturn(Arrays.asList(item01, item02));

        final ResponseEntity<List<Item>> listResponseEntity = itemController.getItemsByName(itemName);
        assertThat(listResponseEntity, notNullValue());
        assertThat(listResponseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(listResponseEntity.getBody(), notNullValue());
        assertThat(listResponseEntity.getBody().isEmpty(), is(false));
        assertThat(listResponseEntity.getBody().size(), is(equalTo(2)));
    }

    /**
     * This test checks if the {@link List<Item>} can not be retrieved by the name of the {@link Item} successfully,
     * because returns null
     */
    @DisplayName("Testing - Getting list of items by the name of the item (Failure - Item Not Found [NULL])")
    @Order(6)
    @Test
    public void testGetItemsByNameItemNullFailure() {
        final String itemName = "Moto G X";
        when(itemRepository.findItemsByName(itemName)).thenReturn(null);

        final ResponseEntity<List<Item>> itemsByName = itemController.getItemsByName(itemName);
        assertThat(itemsByName, notNullValue());
        assertThat(itemsByName.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * This test checks if the {@link List<Item>} can not be retrieved by the name of the {@link Item} successfully,
     * because returns an empty list
     */
    @DisplayName("Testing - Getting list of items by the name of the item (Failure - Item Not Found [EMPTY LIST])")
    @Order(7)
    @Test
    public void testGetItemsByNameItemEmptyFailure() {
        final String itemName = "Moto G X";
        when(itemRepository.findItemsByName(itemName)).thenReturn(new ArrayList<>());

        final ResponseEntity<List<Item>> itemsByName = itemController.getItemsByName(itemName);
        assertThat(itemsByName, notNullValue());
        assertThat(itemsByName.getStatusCodeValue(), is(equalTo(404)));
    }

    /**
     * Here we are returning a pre-configured {@link Item} to be used in the tests
     *
     * @return {@link Item}
     */
    private Item getItem(long itemId, String itemName, String itemDescription, String itemPrice) {
        return Item.builder().id(itemId)
                .name(itemName)
                .description(itemDescription)
                .price(new BigDecimal(itemPrice))
                .build();
    }
}
