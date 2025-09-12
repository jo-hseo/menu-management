package org.menuservice.api.domain.sales.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesRequest {

    private Long menuId;

    @NotBlank
    private String menuName;

    @NotBlank
    private String salePeriod;

    @NotNull(message = "판매량은 필수입니다")
    private Integer volume;

    @NotNull(message = "가격은 필수입니다")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다")
    private BigDecimal price;

    @NotNull(message = "원가는 필수입니다")
    @DecimalMin(value = "0.0", inclusive = false, message = "원가는 0보다 커야 합니다")
    private BigDecimal cost;
}
