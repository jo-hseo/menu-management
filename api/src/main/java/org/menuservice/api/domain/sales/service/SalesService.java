package org.menuservice.api.domain.sales.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.menuservice.api.domain.sales.converter.SalesConverter;
import org.menuservice.api.domain.sales.model.SalesAnalyze;
import org.menuservice.api.domain.sales.model.SalesRequest;
import org.menuservice.db.sales.SalesEntity;
import org.menuservice.db.sales.SalesRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final SalesConverter salesConverter;

    public void save(SalesEntity sales) {
        salesRepository.saveSales(
                sales.getUserId(),
                sales.getMenuId(),
                sales.getMenuName(),
                sales.getSalePeriod(),
                sales.getVolume(),
                sales.getPrice(),
                sales.getCost()
        );
    }

    public List<SalesAnalyze> analyze(Long userId) {
        var recentSalesEntities = salesRepository.readRecentSales(userId);

        List<SalesRequest> recentSales = new ArrayList<>(List.of());
        for(var recentSalesEntity : recentSalesEntities) {
            recentSales.add(salesConverter.toDto(recentSalesEntity));
        }

        var profitabilityThreshold = calculateProfitabilityThreshold(recentSales);
        var preferenceThreshold = calculatePreferenceThreshold(recentSales);

        var result = calculateAnalyzes(recentSales, profitabilityThreshold, preferenceThreshold);

        return result;
    }

    private BigDecimal calculateProfitabilityThreshold(List<SalesRequest> sales) {
        // 합계(가격*판매량) / (전체 판매량)
        var totalSales = sales.stream()
                .map(s -> s.getPrice().multiply(BigDecimal.valueOf(s.getVolume())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var totalVolume = sales.stream()
                .mapToInt(SalesRequest::getVolume)
                .sum();

        if (totalVolume == 0) {
            return BigDecimal.ZERO;
        }

        return totalSales.divide(BigDecimal.valueOf(totalVolume), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePreferenceThreshold(List<SalesRequest> sales) {
        //(전체 판매량) / (메뉴 수) x 0.7
        var totalVolume = sales.stream()
                .mapToInt(SalesRequest::getVolume)
                .sum();

        var totalMenu = sales.stream()
                .map(SalesRequest::getMenuId)
                .distinct()
                .count();

        if (totalMenu == 0) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(totalVolume)
                .divide(BigDecimal.valueOf(totalMenu), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(0.7));
    }

    private List<SalesAnalyze> calculateAnalyzes(List<SalesRequest> sales, BigDecimal profitabilityThreshold, BigDecimal preferenceThreshold) {
        // 수익성 = 판매량 × (판매가 - 원가)
        // 선호도 = 판매량
        List<SalesAnalyze> result = new ArrayList<>();

        for (var sale : sales) {
            BigDecimal profitability = BigDecimal.valueOf(sale.getVolume())
                    .multiply(sale.getPrice().subtract(sale.getCost()));

            BigDecimal preference = BigDecimal.valueOf(sale.getVolume());

            result.add(
                    SalesAnalyze.builder()
                            .menuId(sale.getMenuId())
                            .menuName(sale.getMenuName())
                            .profitability(profitability.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP))
                            .preference(preference)
                            .isWorth(profitability.compareTo(profitabilityThreshold) >= 0)
                            .isFamous(preference.compareTo(preferenceThreshold) >= 0)
                            .build()
            );
        }

        return result;
    }
}
