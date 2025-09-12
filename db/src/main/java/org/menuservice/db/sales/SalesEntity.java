package org.menuservice.db.sales;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.menuservice.db.BaseEntity;

import java.math.BigDecimal;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sales")
@Table(name = "sales")
public class SalesEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long menuId;

    @Column(length = 80)
    private String menuName;

    @Column(nullable = false, columnDefinition = "daterange")
    private String salePeriod;

    @Column(nullable = false)
    private Integer volume;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal cost;
}
