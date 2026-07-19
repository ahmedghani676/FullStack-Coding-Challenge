package com.workflex.workation.constant;

/**
 * User-facing messages returned in the API response envelope. Kept in one place
 * so wording stays consistent and would be straightforward to externalise for
 * i18n later.
 */
public final class ApiMessages {

    public static final String OK = "OK";
    public static final String CREATED = "Created";

    /** Template for {@link com.workflex.workation.exception.InvalidSortFieldException}; args: field, allowedFields. */
    public static final String INVALID_SORT_FIELD = "Cannot sort by '%s'. Allowed fields: %s";

    private ApiMessages() {
    }
}
