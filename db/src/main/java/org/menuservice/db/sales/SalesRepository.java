package org.menuservice.db.sales;

import jakarta.transaction.Transactional;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SalesRepository extends JpaRepository<SalesEntity, Long> {

    @Query(value ="select * " +
            "from sales " +
            "where user_id = :userId and sale_period = (" +
            "select sale_period " +
            "from sales " +
            "where upper(sale_period) = (" +
            "select max(upper(sale_period)) " +
            "from sales " +
            "where ((upper(sale_period) - lower(sale_period)) >= 14)) " +
            "group by sale_period " +
            "order by sale_period " +
            "limit 1" +
            ")", nativeQuery = true)
    public List<SalesEntity> readRecentSales(@Param(value = "userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "insert into sales (user_id, menu_id, menu_name, sale_period, volume, price, cost) " +
            "values (:userId, :menuId, :menuName, daterange(:salePeriod), :volume, :price, :cost)", nativeQuery = true)
    void saveSales(
        @Param("userId") Long userId,
        @Param("menuId") Long menuId,
        @Param("menuName") String menuName,
        @Param("salePeriod") String salePeriod,
        @Param("volume") Integer volume,
        @Param("price") BigDecimal price,
        @Param("cost") BigDecimal cost
    );
}
