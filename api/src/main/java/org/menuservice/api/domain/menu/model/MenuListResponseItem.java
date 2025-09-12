package org.menuservice.api.domain.menu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuListResponseItem {
    //id, 메뉴명, 기획날짜, 판매상태
    private Long menuId;
    private String menuName;
    private String proposalDate;
    private Boolean state;
}
