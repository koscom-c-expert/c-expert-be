package com.koscom.cexpert.controller;

import com.koscom.cexpert.dto.ApiResponse;
import com.koscom.cexpert.dto.CreateStockRequest;
import com.koscom.cexpert.dto.UpdateStockRequest;
import com.koscom.cexpert.model.TestStock;
import com.koscom.cexpert.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // 주식 생성
    @PostMapping
    public ApiResponse<TestStock> createStock(@RequestBody CreateStockRequest req) {
        TestStock createdTestStock = stockService.createStock(req);
        return ApiResponse.success(createdTestStock);
    }

    // 모든 주식 조회
    @GetMapping
    public ApiResponse<List<TestStock>> getAllStocks() {
        List<TestStock> testStocks = stockService.getAllStocks();
        return ApiResponse.success(testStocks);
    }

    // 주식 조회
    @GetMapping("/{id}")
    public ApiResponse<TestStock> getStockById(@PathVariable Long id) {
        TestStock testStock = stockService.getStockById(id);
        return ApiResponse.success(testStock);
    }

    // 주식 정보 수정
    @PutMapping("/{id}")
    public ApiResponse<TestStock> updateStock(
            @PathVariable Long id,
            @RequestBody UpdateStockRequest req) {
        TestStock updatedTestStock = stockService.updateStock(id, req);
        return ApiResponse.success(updatedTestStock);
    }

    // 주식 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ApiResponse.success(null);
    }
}
