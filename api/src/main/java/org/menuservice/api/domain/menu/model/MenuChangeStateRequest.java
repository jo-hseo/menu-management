package org.menuservice.api.domain.menu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuChangeStateRequest {
    //메뉴id, 판매상태
    private Long menuId;
    private Boolean state;
}
