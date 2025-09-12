package org.menuservice.api.domain.menu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuSelectionItem {
    private Long menuId;
    private String menuName;
}
