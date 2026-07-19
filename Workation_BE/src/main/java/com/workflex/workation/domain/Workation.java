package com.workflex.workation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "workation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workation {

    /** Business key as assigned in the source CSV, e.g. "w1". */
    @Id
    @Column(name = "workation_id", nullable = false, updatable = false)
    private String workationId;

    @Column(nullable = false)
    private String employee;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int workingDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel risk;
}
