package org.menuservice.api.domain.sales.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesAnalyze {
    private Long menuId;
    private String menuName;
    private BigDecimal profitability;
    private BigDecimal preference;
    private boolean isWorth;
    private boolean isFamous;
}
