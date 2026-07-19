package com.workflex.workation.service;

import com.workflex.workation.dto.SortDirection;
import com.workflex.workation.dto.WorkationResponse;

import java.util.List;

public interface WorkationService {

    /**
     * @param sortBy    entity field to sort by, or {@code null} for the default (employee)
     * @param direction sort direction; {@code null} is treated as ascending
     */
    List<WorkationResponse> getWorkations(String sortBy, SortDirection direction);
}
