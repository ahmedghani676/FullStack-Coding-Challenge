package com.workflex.workation.dto;

import org.springframework.data.domain.Sort;

/**
 * Sort direction for the workation listing endpoint. Replaces the former
 * "asc"/"desc" magic strings with a type-safe value that is bound from the
 * {@code direction} query parameter (case-insensitively) by
 * {@link com.workflex.workation.web.SortDirectionConverter}.
 */
public enum SortDirection {

    ASC,
    DESC;

    /** Maps to the equivalent Spring Data {@link Sort.Direction}. */
    public Sort.Direction toSpringDirection() {
        return this == DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
