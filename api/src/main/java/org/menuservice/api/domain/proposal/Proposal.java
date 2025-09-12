package org.menuservice.api.domain.proposal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proposal {
    private String title;

//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastUpdatedDate;

    private ProposalDetail proposalDetail;
}
