package org.menuservice.db.proposal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Struct;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Struct(name = "proposal")
public class ProposalEntity {

    private String title;

//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastUpdatedDate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String proposalDetail;
}

