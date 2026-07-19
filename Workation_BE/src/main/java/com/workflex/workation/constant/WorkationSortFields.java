package com.workflex.workation.constant;

import java.util.Set;

/**
 * Sortable field names for the workation listing. These must match the
 * corresponding {@code Workation} entity property names, since they are passed
 * straight to Spring Data's {@code Sort}. Centralised here so the controller,
 * service and tests reference one source of truth instead of magic strings.
 */
public final class WorkationSortFields {

    public static final String WORKATION_ID = "workationId";
    public static final String EMPLOYEE = "employee";
    public static final String ORIGIN = "origin";
    public static final String DESTINATION = "destination";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String WORKING_DAYS = "workingDays";
    public static final String RISK = "risk";

    public static final Set<String> ALLOWED = Set.of(
            WORKATION_ID, EMPLOYEE, ORIGIN, DESTINATION, START_DATE, END_DATE, WORKING_DAYS, RISK
    );

    public static final String DEFAULT = EMPLOYEE;

    private WorkationSortFields() {
    }
}
