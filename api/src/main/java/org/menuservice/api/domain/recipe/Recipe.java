package org.menuservice.api.domain.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    @JsonProperty(required = true, value = "ingredients")
    private List<Ingredient> ingredients;

    @JsonProperty(required = true, value = "procedure")
    private String procedure;

    @JsonProperty(required = true, value = "design")
    private String design;

    @JsonProperty(required = true, value = "design_description")
    private String designDescription;

    @JsonProperty(required = true, value = "cost")
    private BigDecimal cost;
}
