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
public class MenuUpdateRequest {
    //메뉴id , 메뉴명, 레시피, 기획서, 가격
    private Long menuId;
    private String menuName;
    private BigDecimal price;
    private Proposal proposal;
    private Recipe recipe;
}
