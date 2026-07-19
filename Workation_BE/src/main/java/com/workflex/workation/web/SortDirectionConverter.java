package com.workflex.workation.web;

import com.workflex.workation.dto.SortDirection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Binds the {@code direction} query parameter to a {@link SortDirection},
 * case-insensitively. Blank or unrecognised values fall back to {@code ASC} so a
 * malformed direction never fails the request (mirroring the previous lenient
 * string handling).
 */
@Component
public class SortDirectionConverter implements Converter<String, SortDirection> {

    @Override
    public SortDirection convert(String source) {
        return SortDirection.DESC.name().equalsIgnoreCase(source.trim())
                ? SortDirection.DESC
                : SortDirection.ASC;
    }
}
