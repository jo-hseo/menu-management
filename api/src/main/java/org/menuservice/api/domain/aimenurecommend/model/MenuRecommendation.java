package org.menuservice.api.domain.aimenurecommend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.menuservice.api.domain.recipe.Recipe;

public class MenuRecommendation {
    @JsonProperty(required = true, value = "recipe")
    Recipe recipe;

    @JsonProperty(required = true, value = "price")
    Integer price;
}
