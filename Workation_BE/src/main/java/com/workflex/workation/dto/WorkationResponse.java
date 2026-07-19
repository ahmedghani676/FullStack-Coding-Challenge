package com.workflex.workation.dto;

import com.workflex.workation.domain.RiskLevel;

import java.time.LocalDate;

public record WorkationResponse(
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
