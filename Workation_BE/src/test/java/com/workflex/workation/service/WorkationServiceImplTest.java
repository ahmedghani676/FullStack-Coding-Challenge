package com.workflex.workation.service;

import com.workflex.workation.domain.RiskLevel;
import com.workflex.workation.domain.Workation;
import com.workflex.workation.dto.SortDirection;
import com.workflex.workation.dto.WorkationMapper;
import com.workflex.workation.dto.WorkationResponse;
import com.workflex.workation.exception.InvalidSortFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkationServiceImplTest {

    @Mock
    private com.workflex.workation.repository.WorkationRepository workationRepository;

    @Mock
    private WorkationMapper workationMapper;

    @Captor
    private ArgumentCaptor<Sort> sortCaptor;

    private WorkationServiceImpl service;

    private final Workation entity = Workation.builder()
            .workationId("w1")
            .employee("Steffen Jacobs")
            .origin("Germany")
            .destination("United States")
            .startDate(LocalDate.of(2024, 1, 2))
            .endDate(LocalDate.of(2024, 12, 31))
            .workingDays(65)
            .risk(RiskLevel.HIGH)
            .build();

    private final WorkationResponse response = new WorkationResponse(
            "w1", "Steffen Jacobs", "Germany", "United States",
            LocalDate.of(2024, 1, 2), LocalDate.of(2024, 12, 31), 65, RiskLevel.HIGH);

    private void setUp() {
        service = new WorkationServiceImpl(workationRepository, workationMapper);
    }

    @Test
    void defaultsToSortingByEmployeeAscendingWhenNoSortGiven() {
        setUp();
        when(workationRepository.findAll(sortCaptor.capture())).thenReturn(List.of(entity));
        when(workationMapper.toResponse(entity)).thenReturn(response);

        List<WorkationResponse> result = service.getWorkations(null, null);

        assertThat(result).containsExactly(response);
        Sort.Order order = sortCaptor.getValue().getOrderFor("employee");
        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    void sortsByRequestedFieldAndDescendingDirection() {
        setUp();
        when(workationRepository.findAll(sortCaptor.capture())).thenReturn(List.of(entity));
        when(workationMapper.toResponse(entity)).thenReturn(response);

        service.getWorkations("workingDays", SortDirection.DESC);

        Sort.Order order = sortCaptor.getValue().getOrderFor("workingDays");
        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    void rejectsSortFieldThatIsNotWhitelisted() {
        setUp();
        assertThatThrownBy(() -> service.getWorkations("password", SortDirection.ASC))
                .isInstanceOf(InvalidSortFieldException.class)
                .hasMessageContaining("password");
    }

    @Test
    void mapsEveryEntityReturnedByTheRepository() {
        setUp();
        when(workationRepository.findAll(sortCaptor.capture())).thenReturn(List.of(entity));
        when(workationMapper.toResponse(entity)).thenReturn(response);

        service.getWorkations(null, null);

        verify(workationMapper).toResponse(entity);
    }
}
