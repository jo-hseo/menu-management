package org.menuservice.api.domain.proposal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProposalDetail {
    private String name;
    private String goal;
    private String target;
    private String when;
    private String where;
    private String price;
    private String volume;
}
