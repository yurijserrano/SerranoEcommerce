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

/**
 * The {@link UserOrder} will be persisted into the database.
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
 * @see <a href="https://vladmihalcea.com/manytoone-jpa-hibernate/">@ManyToOne I</a>
 * @see <a href="https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/">@ManyToOne II</a>
 * @see <a href="http://www.thejavageek.com/2014/01/15/jpa-many-one-association/">@ManyToOne III</a>
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_order")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @Column
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonProperty
    @Column
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @JsonProperty
    private User user;

    @JsonProperty
    @Column
    private BigDecimal total;

    /**
     * Here it's created the order of the {@link User} based on your own {@link Cart}
     *
     * @param cart {@link Cart}
     * @return {@link UserOrder}
     */
    public static UserOrder createFromCart(Cart cart) {
        UserOrder order = new UserOrder();
        order.setItems(new ArrayList<>(cart.getItems()));
        order.setTotal(cart.getTotal());
        order.setUser(cart.getUser());
        return order;
    }
}
