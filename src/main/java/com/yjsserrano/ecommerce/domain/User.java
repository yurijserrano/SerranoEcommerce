package com.yjsserrano.ecommerce.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The {@link User} will be persisted into the database.
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://projectlombok.org/api/lombok/package-summary.html">Lombok I</a>
 * @see <a href="https://springframework.guru/spring-boot-with-lombok-part-1/">Lombok II</a>
 * @see <a href="https://www.baeldung.com/lombok-builder">Lombok III</a>
 * @see <a href="https://projectlombok.org/features/Data">Lombok IV</a>
 * @see <a href="https://projectlombok.org/features/Builder">Lombok V</a>
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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;

    @Column(nullable = false, unique = true)
    @JsonProperty
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonIgnore
    private Cart cart;
}
