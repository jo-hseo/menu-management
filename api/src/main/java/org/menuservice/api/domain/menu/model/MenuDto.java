package org.menuservice.api.domain.menu.model;

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
public class MenuDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Proposal proposal;
    private Recipe recipe;
    private Boolean onSale;
    private Long userId;
}
