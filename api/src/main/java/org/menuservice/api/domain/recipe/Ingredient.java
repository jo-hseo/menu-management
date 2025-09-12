package org.menuservice.api.domain.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    @JsonProperty(required = true, value = "name")
    private String name;

    @JsonProperty(required = true, value = "amount")
    private Integer amount;

    @JsonProperty(required = true, value = "amount_unit")
    private String amountUnit;

    @JsonProperty(required = true, value = "price")
    private BigDecimal price;
}
