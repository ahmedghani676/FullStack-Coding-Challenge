/**
 * Travel risk classification for a workation, mirroring the backend enum. The
 * string values match the JSON emitted by the API. NO and LOW are both surfaced
 * as "No risk" in the UI (with different colors); HIGH is surfaced as
 * "High risk". See {@link RiskLabelPipe}.
 */
export enum RiskLevel {
  No = 'NO',
  Low = 'LOW',
  High = 'HIGH',
}

/**
 * A single workation as returned by GET /workflex/workation.
 * Dates arrive as ISO strings (yyyy-MM-dd) and are formatted for display
 * in the template via Angular's DatePipe (dd/MM/yyyy).
 */
export interface Workation {
  workationId: string;
  employee: string;
  origin: string;
  destination: string;
  startDate: string;
  endDate: string;
  workingDays: number;
  risk: RiskLevel;
}
