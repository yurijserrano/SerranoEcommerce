package com.yjsserrano.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yjsserrano.ecommerce.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * The {@link CartDTO} will receive the info from the client related to the {@link Cart}
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://stackoverflow.com/questions/1051182/what-is-data-transfer-object">DTO I</a>
 * @see <a href="https://martinfowler.com/eaaCatalog/dataTransferObject.html">DTO II</a>
 * @see <a href="https://www.devmedia.com.br/diferenca-entre-os-patterns-po-pojo-bo-dto-e-vo/28162">DTO III</a>
 * @see <a href="https://medium.com/@Colin_But/dissecting-the-dto-pattern-ac3e54d0e4c8">DTO IV</a>
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    @JsonProperty
    @NotNull(message = "Username can not be null")
    private String username;

    @JsonProperty
    @NotNull(message = "Item Id can not be null")
    private long itemId;

    @JsonProperty
    @NotNull(message = "Quantity can not be null")
    private int quantity;
}
