package com.koscom.cexpert.controller;

import com.koscom.cexpert.dto.ApiResponse;
import com.koscom.cexpert.dto.CreateStockRequest;
import com.koscom.cexpert.dto.UpdateStockRequest;
import com.koscom.cexpert.model.Stock;
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
    public ApiResponse<Stock> createStock(@RequestBody CreateStockRequest req) {
        Stock createdStock = stockService.createStock(req);
        return ApiResponse.success(createdStock);
    }

    // 모든 주식 조회
    @GetMapping
    public ApiResponse<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ApiResponse.success(stocks);
    }

    // 주식 조회
    @GetMapping("/{id}")
    public ApiResponse<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        return ApiResponse.success(stock);
    }

    // 주식 정보 수정
    @PutMapping("/{id}")
    public ApiResponse<Stock> updateStock(
            @PathVariable Long id,
            @RequestBody UpdateStockRequest req) {
        Stock updatedStock = stockService.updateStock(id, req);
        return ApiResponse.success(updatedStock);
    }

    // 주식 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ApiResponse.success(null);
    }
}
