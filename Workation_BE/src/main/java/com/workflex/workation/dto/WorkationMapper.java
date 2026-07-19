package com.workflex.workation.dto;

import com.workflex.workation.domain.Workation;
import org.springframework.stereotype.Component;

@Component
public class WorkationMapper {

    public WorkationResponse toResponse(Workation workation) {
        return new WorkationResponse(
                workation.getWorkationId(),
                workation.getEmployee(),
                workation.getOrigin(),
                workation.getDestination(),
                workation.getStartDate(),
                workation.getEndDate(),
                workation.getWorkingDays(),
                workation.getRisk()
        );
    }
}
