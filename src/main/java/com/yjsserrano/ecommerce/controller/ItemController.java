package com.yjsserrano.ecommerce.controller;

import com.yjsserrano.ecommerce.domain.Item;
import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * Rest Controller {@link Item} endpoint
 * <p>
 * Handles web requests related to {@link Item} of the {@link User}
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
@RequestMapping("/api/item")
public class ItemController {


    @Autowired
    private ItemRepository itemRepository;

    /**
     * Here it's returning a {@link List<Item>}
     *
     * @return {@link List<Item>}
     */
    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    /**
     * Here it's returning an {@link Item} by the id
     *
     * @return {@link ResponseEntity<Item>}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return ResponseEntity.of(itemRepository.findById(id));
    }

    /**
     * Here it's returning a {@link List<Item>} by the {@link Item} name
     *
     * @return {@link List<Item>}
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
        List<Item> items = itemRepository.findItemsByName(name);
        return isNull(items) || items.isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(items);
    }
}
