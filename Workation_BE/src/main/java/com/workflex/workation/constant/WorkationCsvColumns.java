package com.workflex.workation.constant;

import java.util.List;

/**
 * Column names of the workations CSV, in file order. These are the CSV header
 * labels (e.g. "start"/"end"), which intentionally differ from the entity
 * property names in {@link WorkationSortFields} (e.g. "startDate"/"endDate").
 * Exposed as an immutable {@link List} so the ordered header can't be mutated.
 */
public final class WorkationCsvColumns {

    public static final String WORKATION_ID = "workationId";
    public static final String EMPLOYEE = "employee";
    public static final String ORIGIN = "origin";
    public static final String DESTINATION = "destination";
    public static final String START = "start";
    public static final String END = "end";
    public static final String WORKING_DAYS = "workingDays";
    public static final String RISK = "risk";

    /** CSV columns in the exact order they appear in the file. */
    public static final List<String> HEADER = List.of(
            WORKATION_ID, EMPLOYEE, ORIGIN, DESTINATION, START, END, WORKING_DAYS, RISK
    );

    private WorkationCsvColumns() {
    }
}
