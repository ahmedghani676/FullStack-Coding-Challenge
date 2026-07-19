package com.workflex.workation.csv;

import com.workflex.workation.domain.Workation;
import org.springframework.stereotype.Component;

/**
 * Maps a parsed {@link WorkationCsvRecord} onto a persistable {@code Workation}
 * entity, mirroring {@code WorkationMapper} on the outbound (entity → response)
 * side. This is the only place that bridges the CSV model and the entity.
 */
@Component
public class WorkationCsvMapper {

    public Workation toEntity(WorkationCsvRecord record) {
        return Workation.builder()
                .workationId(record.workationId())
                .employee(record.employee())
                .origin(record.origin())
                .destination(record.destination())
                .startDate(record.startDate())
                .endDate(record.endDate())
                .workingDays(record.workingDays())
                .risk(record.risk())
                .build();
    }
}
