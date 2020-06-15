package com.yjsserrano.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The {@link Item} will be persisted into the database.
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://projectlombok.org/api/lombok/package-summary.html">Lombok I</a>
 * @see <a href="https://springframework.guru/spring-boot-with-lombok-part-1/">Lombok II</a>
 * @see <a href="https://www.baeldung.com/lombok-builder">Lombok III</a>
 * @see <a href="https://projectlombok.org/features/Data">Lombok IV</a>
 * @see <a href="https://projectlombok.org/features/Builder">Lombok V</a>
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column(nullable = false)
    @JsonProperty
    private String name;

    @Column(nullable = false)
    @JsonProperty
    private BigDecimal price;

    @Column(nullable = false)
    @JsonProperty
    private String description;
}
