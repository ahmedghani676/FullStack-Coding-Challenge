package com.workflex.workation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Query parameters for the workation listing endpoint. Bound from the request
 * query string via {@code @ModelAttribute}, which groups the sort options into a
 * single object instead of loose method parameters and gives one place to add
 * further filters (pagination, search, ...) as the endpoint grows.
 *
 * <p>Only the JavaBeans binding contract is needed here — a no-arg constructor
 * for the binder to instantiate, plus setters/getters — so no builder or
 * all-args constructor.
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkationRequest {

    /** Entity field to sort by; {@code null}/blank falls back to the service default. */
    private String sortBy;

    /** Sort direction; defaults to ascending when the query parameter is omitted. */
    private SortDirection direction = SortDirection.ASC;
}
