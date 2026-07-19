package com.workflex.workation.exception;

import com.workflex.workation.constant.ApiMessages;

import java.util.Set;

public class InvalidSortFieldException extends RuntimeException {

    public InvalidSortFieldException(String field, Set<String> allowedFields) {
        super(ApiMessages.INVALID_SORT_FIELD.formatted(field, allowedFields));
    }
}
