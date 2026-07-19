package com.workflex.workation.web;

import com.workflex.workation.dto.WorkationRequest;
import com.workflex.workation.dto.WorkationResponse;
import com.workflex.workation.response.ApiResponse;
import com.workflex.workation.service.WorkationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workflex/workation")
@RequiredArgsConstructor
public class WorkationController {

    private final WorkationService workationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkationResponse>>> getWorkations(
            @ModelAttribute WorkationRequest request) {
        List<WorkationResponse> workations =
                workationService.getWorkations(request.getSortBy(), request.getDirection());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(workations));
    }
}
