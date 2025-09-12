package org.menuservice.api.domain.sales.converter;

import org.menuservice.api.domain.sales.model.SalesRequest;
import org.menuservice.db.sales.SalesEntity;
import org.springframework.stereotype.Service;

@Service
public class SalesConverter {

    public SalesRequest toDto(SalesEntity salesEntity) {

        return SalesRequest.builder()
                .menuId(salesEntity.getMenuId())
                .menuName(salesEntity.getMenuName())
                .salePeriod(salesEntity.getSalePeriod())
                .volume(salesEntity.getVolume())
                .price(salesEntity.getPrice())
                .cost(salesEntity.getCost())
                .build();
    }

    public SalesEntity toEntity(SalesRequest salesRequest, Long userId) {

        return SalesEntity.builder()
                .userId(userId)
                .menuId(salesRequest.getMenuId())
                .menuName(salesRequest.getMenuName())
                .salePeriod(salesRequest.getSalePeriod())
                .volume(salesRequest.getVolume())
                .price(salesRequest.getPrice())
                .cost(salesRequest.getCost())
                .build();
    }
}
