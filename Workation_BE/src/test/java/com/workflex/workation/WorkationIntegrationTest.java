package com.workflex.workation;

import com.workflex.workation.dto.WorkationResponse;
import com.workflex.workation.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class WorkationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ParameterizedTypeReference<ApiResponse<List<WorkationResponse>>> WORKATION_LIST =
            new ParameterizedTypeReference<>() {
            };

    @Test
    void importsCsvOnStartupAndServesItFromTheApi() {
        ResponseEntity<ApiResponse<List<WorkationResponse>>> response =
                restTemplate.exchange("/workflex/workation", HttpMethod.GET, null, WORKATION_LIST);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        ApiResponse<List<WorkationResponse>> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.status()).isEqualTo(200);
        assertThat(body.data()).hasSize(5);
        assertThat(body.data())
                .extracting(WorkationResponse::workationId)
                .containsExactlyInAnyOrder("w1", "w2", "w3", "w4", "w5");
    }

    @Test
    void sortsByWorkingDaysDescendingWhenRequested() {
        ResponseEntity<ApiResponse<List<WorkationResponse>>> response = restTemplate.exchange(
                "/workflex/workation?sortBy=workingDays&direction=desc", HttpMethod.GET, null, WORKATION_LIST);

        List<WorkationResponse> data = response.getBody().data();
        assertThat(data.get(0).workationId()).isEqualTo("w3");
        assertThat(data.get(0).workingDays()).isEqualTo(131);
        assertThat(data.get(data.size() - 1).workingDays()).isEqualTo(1);
    }

    @Test
    void rejectsAnUnknownSortFieldWithBadRequest() {
        ResponseEntity<ApiResponse<List<WorkationResponse>>> response = restTemplate.exchange(
                "/workflex/workation?sortBy=doesNotExist", HttpMethod.GET, null, WORKATION_LIST);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody().status()).isEqualTo(400);
    }
}
