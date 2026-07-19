package com.workflex.workation.web;

import com.workflex.workation.domain.RiskLevel;
import com.workflex.workation.dto.SortDirection;
import com.workflex.workation.dto.WorkationResponse;
import com.workflex.workation.exception.InvalidSortFieldException;
import com.workflex.workation.service.WorkationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkationController.class)
class WorkationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkationService workationService;

    private static final WorkationResponse SAMPLE = new WorkationResponse(
            "w1", "Steffen Jacobs", "Germany", "United States",
            LocalDate.of(2024, 1, 2), LocalDate.of(2024, 12, 31), 65, RiskLevel.HIGH);

    @Test
    void returnsWorkationsAsJson() throws Exception {
        when(workationService.getWorkations(any(), any())).thenReturn(List.of(SAMPLE));

        mockMvc.perform(get("/workflex/workation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data[0].workationId").value("w1"))
                .andExpect(jsonPath("$.data[0].employee").value("Steffen Jacobs"))
                .andExpect(jsonPath("$.data[0].origin").value("Germany"))
                .andExpect(jsonPath("$.data[0].destination").value("United States"))
                .andExpect(jsonPath("$.data[0].workingDays").value(65))
                .andExpect(jsonPath("$.data[0].risk").value("HIGH"));
    }

    @Test
    void passesSortParametersThroughToTheService() throws Exception {
        when(workationService.getWorkations(eq("workingDays"), eq(SortDirection.DESC)))
                .thenReturn(List.of(SAMPLE));

        mockMvc.perform(get("/workflex/workation").param("sortBy", "workingDays").param("direction", "desc"))
                .andExpect(status().isOk());
    }

    @Test
    void defaultsDirectionToAscWhenNotProvided() throws Exception {
        when(workationService.getWorkations(any(), eq(SortDirection.ASC))).thenReturn(List.of(SAMPLE));

        mockMvc.perform(get("/workflex/workation").param("sortBy", "employee"))
                .andExpect(status().isOk());

        verify(workationService).getWorkations("employee", SortDirection.ASC);
    }

    @Test
    void returnsBadRequestForInvalidSortField() throws Exception {
        when(workationService.getWorkations(eq("nope"), any()))
                .thenThrow(new InvalidSortFieldException("nope", Set.of("employee")));

        mockMvc.perform(get("/workflex/workation").param("sortBy", "nope"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("nope")));
    }
}
