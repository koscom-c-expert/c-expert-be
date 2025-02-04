package com.koscom.cexpert.controller;

import com.koscom.cexpert.dto.*;
import com.koscom.cexpert.service.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classification")
@RequiredArgsConstructor
public class ClassificationController {
    private final LLMService llmService;

    @PostMapping
    public ApiResponse<List<ClassificationResponse>> classify(@RequestBody ClassificationRequest req) {
        List<ClassificationResponse> res = llmService.classifyStocks(req);
        return ApiResponse.success(res);
    }

}
