package com.koscom.cexpert.controller;

import com.koscom.cexpert.dto.*;
import com.koscom.cexpert.model.TestStock;
import com.koscom.cexpert.service.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/v1/classification")
@RequiredArgsConstructor
public class ClassificationController {
    private final LLMService llmService;

    @PostMapping
    public ApiResponse<CreateClassificationResponse> createClassification(@RequestBody CreateClassificationRequest req) {
        /*
        List<StockCategory> stockCategories = new ArrayList<StockCategory>();
        stockCategories.add(new StockCategory(1, "반도체"));
        stockCategories.add(new StockCategory(2, "파운더리"));
        stockCategories.add(new StockCategory(3, "기타"));
        List<String> stocks = Arrays.asList("toyota", "nvidia", "samsung", "nestle");
         */
        List<String> distinctCategories = req.getStockCategories().stream()
                .distinct()
                .toList();
        List<StockCategory> stockCategories = IntStream.range(0, distinctCategories.size())
                .mapToObj(index -> new StockCategory(index, distinctCategories.get(index)))
                .toList();
        List<Stock> stocks = llmService.classifyStocks(stockCategories, req.getStocks());
        return ApiResponse.success(new CreateClassificationResponse(stockCategories, stocks));
    }
}
