package com.koscom.cexpert.controller;

import com.koscom.cexpert.dto.ApiResponse;
import com.koscom.cexpert.dto.RebalanceRequest;
import com.koscom.cexpert.dto.RebalanceResponse;
import com.koscom.cexpert.service.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rebalance")
@RequiredArgsConstructor
public class RebalanceController {
    private final LLMService llmService;

    @PostMapping
    public ApiResponse<List<RebalanceResponse>> rebalance(@RequestBody List<RebalanceRequest> req) {
        List<RebalanceResponse> res = llmService.rebalanceCategories(req);
        return ApiResponse.success(res);
    }
}
