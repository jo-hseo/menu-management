package org.menuservice.api.domain.menu.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.menuservice.api.domain.recipe.Recipe;
import org.menuservice.api.domain.proposal.Proposal;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDevelopRequest {
    //메뉴명, 기획서, 레시피, 가격
    @NotBlank
    @JsonProperty(value = "menu_name")
    private String name;
    private BigDecimal price;
    private Proposal proposal;
    private Recipe recipe;
}
