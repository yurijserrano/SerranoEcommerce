package com.yjsserrano.ecommerce.controller;

import com.yjsserrano.ecommerce.domain.Item;
import com.yjsserrano.ecommerce.domain.User;
import com.yjsserrano.ecommerce.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @see <a href="https://lankydan.dev/2019/01/09/configuring-logback-with-spring-boot">Logback I</a>
 * @see <a href="https://www.javaguides.net/2018/09/spring-boot-2-logging-slf4j-logback-and-log4j-example.html">Logback II</a>
 * @see <a href="https://dzone.com/articles/configuring-logback-with-spring-boot">Logback III</a>
 * @see <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging">Logback IV</a>
 * @see <a href="https://examples.javacodegeeks.com/enterprise-java/logback/logback-configuration-example/">Logback V</a>
 * @since 1.0
 */
@RestController
@RequestMapping("/api/item")
public class ItemController {
    private static final Logger log = LoggerFactory.getLogger("splunk.logger");

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Here it's returning a {@link List<Item>}
     *
     * @return {@link List<Item>}
     */
    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        log.info("Method: getItems | Status: Success | Message: The list of items were retrieved successfully");
        return ResponseEntity.ok(itemRepository.findAll());
    }

    /**
     * Here it's returning an {@link Item} by the id
     *
     * @return {@link ResponseEntity<Item>}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        log.info("Method: getItemById | Status: Analyzing | Message: The item {} it's being retrieved", id);
        return ResponseEntity.of(itemRepository.findById(id));
    }

    /**
     * Here it's returning a {@link List<Item>} by the {@link Item} name
     *
     * @return {@link List<Item>}
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
        log.info("Method: getItemsByName | Status: Analyzing | Message: The item {} it's being retrieved", name);
        List<Item> items = itemRepository.findItemsByName(name);
        return isNull(items) || items.isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(items);
    }
}
