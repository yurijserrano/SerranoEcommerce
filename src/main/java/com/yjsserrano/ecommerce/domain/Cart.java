package com.yjsserrano.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * The {@link Cart} will be persisted into the database.
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://projectlombok.org/api/lombok/package-summary.html">Lombok I</a>
 * @see <a href="https://springframework.guru/spring-boot-with-lombok-part-1/">Lombok II</a>
 * @see <a href="https://www.baeldung.com/lombok-builder">Lombok III</a>
 * @see <a href="https://projectlombok.org/features/Data">Lombok IV</a>
 * @see <a href="https://projectlombok.org/features/Builder">Lombok V</a>
 * @see <a href="https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate">@ManyToMany I</a>
 * @see <a href="https://www.baeldung.com/jpa-many-to-many">@ManyToMany II</a>
 * @see <a href="https://datacadamia.com/jpa/many-to-many">@ManyToMany III</a>
 * @see <a href="http://www.java2s.com/Tutorial/Java/0355__JPA/0200__Many-To-Many-Mapping.htm">@ManyToMany IV</a>
 * @see <a href="https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate">@OneToOne I</a>
 * @see <a href="https://www.baeldung.com/jpa-one-to-one">@OneToOne II</a>
 * @see <a href="https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-one-mapping-example">@OneToOne III</a>
 * @see <a href="https://www.javatpoint.com/jpa-one-to-one-mapping">@OneToOne IV</a>
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @Column
    private Long id;

    @ManyToMany
    @JsonProperty
    @Column
    private List<Item> items;

    @OneToOne(mappedBy = "cart")
    @JsonProperty
    private User user;

    @Column
    @JsonProperty
    private BigDecimal total;

    /**
     * Here it's added an item to the cart
     *
     * @param item {@link Item}
     */
    public void addItem(Item item) {
        if (isNull(items)) {
            items = new ArrayList<>();
        }
        items.add(item);
        if (isNull(total)) {
            total = new BigDecimal(0);
        }
        total = total.add(item.getPrice());
    }

    /**
     * Here it's removed an item from the cart
     *
     * @param item {@link Item}
     */
    public void removeItem(Item item) {
        if (isNull(items)) {
            items = new ArrayList<>();
        }
        items.remove(item);
        if (isNull(total)) {
            total = new BigDecimal(0);
        }
        total = total.subtract(item.getPrice());
    }
}
