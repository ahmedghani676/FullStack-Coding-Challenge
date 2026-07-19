package com.workflex.workation.domain;

/**
 * Travel risk classification for a workation, as provided by the source CSV.
 * NO and LOW are both surfaced as "No risk" in the UI but are kept distinct
 * since the CSV distinguishes them and the frontend renders them with different colors.
 */
public enum RiskLevel {
    NO,
    LOW,
    HIGH
}
