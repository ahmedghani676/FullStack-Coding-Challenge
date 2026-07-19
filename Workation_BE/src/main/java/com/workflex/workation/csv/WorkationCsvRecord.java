package com.workflex.workation.csv;

import com.workflex.workation.domain.RiskLevel;

import java.time.LocalDate;

/**
 * Typed representation of a single row in the workations CSV. This is the
 * import boundary's own model: {@link WorkationCsvParser} produces it from raw
 * text, and {@link WorkationCsvMapper} converts it into a {@code Workation}
 * entity. Keeping it separate decouples the file format from the persistence
 * model so each can evolve independently.
 */
public record WorkationCsvRecord(
        String workationId,
        String employee,
        String origin,
        String destination,
        LocalDate startDate,
        LocalDate endDate,
        int workingDays,
        RiskLevel risk
) {
}
