package org.menuservice.db.menu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.menuservice.db.BaseEntity;
import org.menuservice.db.proposal.ProposalEntity;

import java.math.BigDecimal;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "menu")
@Table(name = "menu")
public class MenuEntity extends BaseEntity {

    @Column(length = 80, nullable = false)
    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Embedded
    @Column(columnDefinition = "proposal")
    private ProposalEntity proposal;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private String recipe;

    @Column(name= "onSale", nullable = false)
    private Boolean onSale;

    @Column(name = "userId")
    private Long userId;
}
