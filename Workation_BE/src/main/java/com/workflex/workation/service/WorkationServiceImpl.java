package com.workflex.workation.service;

import com.workflex.workation.constant.WorkationSortFields;
import com.workflex.workation.dto.SortDirection;
import com.workflex.workation.dto.WorkationMapper;
import com.workflex.workation.dto.WorkationResponse;
import com.workflex.workation.exception.InvalidSortFieldException;
import com.workflex.workation.repository.WorkationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkationServiceImpl implements WorkationService {

    private final WorkationRepository workationRepository;
    private final WorkationMapper workationMapper;

    @Override
    public List<WorkationResponse> getWorkations(String sortBy, SortDirection direction) {
        Sort sort = buildSort(sortBy, direction);
        return workationRepository.findAll(sort).stream()
                .map(workationMapper::toResponse)
                .toList();
    }

    private Sort buildSort(String sortBy, SortDirection direction) {
        String field = sortBy == null || sortBy.isBlank() ? WorkationSortFields.DEFAULT : sortBy;
        if (!WorkationSortFields.ALLOWED.contains(field)) {
            throw new InvalidSortFieldException(field, WorkationSortFields.ALLOWED);
        }
        SortDirection resolved = direction == null ? SortDirection.ASC : direction;
        return Sort.by(resolved.toSpringDirection(), field);
    }
}
