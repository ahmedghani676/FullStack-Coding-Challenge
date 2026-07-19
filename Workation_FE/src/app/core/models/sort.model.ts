/**
 * Sort direction sent to the backend listing endpoint. The string values map
 * directly onto the `direction` query parameter (and the backend's
 * {@code SortDirection} enum).
 */
export enum SortDirection {
  Asc = 'asc',
  Desc = 'desc',
}

/**
 * A sortable table column: the visible header plus the backend entity field
 * it maps to. The `field` values must match the server's whitelist
 * (WorkationSortFields), since they are sent as the `sortBy` query parameter.
 */
export interface SortableColumn {
  header: string;
  field: string;
  /** Right/centre-aligned numeric column (e.g. working days). */
  numeric?: boolean;
}
