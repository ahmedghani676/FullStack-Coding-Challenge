/**
 * Standard response envelope used by every backend endpoint, mirroring the
 * server-side {@code ApiResponse<T>} record. The HTTP status is echoed in the
 * body so success and error payloads share one predictable shape.
 */
export interface ApiResponse<T> {
  status: number;
  message: string;
  data: T;
}
