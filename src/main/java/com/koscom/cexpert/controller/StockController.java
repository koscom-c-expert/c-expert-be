package com.koscom.cexpert.controller;

import com.koscom.cexpert.dto.ApiResponse;
import com.koscom.cexpert.dto.StockRequest;
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

    @PostMapping
    public ApiResponse<Stock> createStock(@RequestBody StockRequest req) {
        Stock createdStock = stockService.createStock(req);
        return ApiResponse.success(createdStock);
    }

    @GetMapping
    public ApiResponse<List<Stock>> getAllStocksByUserId(@RequestParam String userId) {
        List<Stock> stocks = stockService.getAllStocksByUserId(userId);
        return ApiResponse.success(stocks);
    }

    @GetMapping("/{id}")
    public ApiResponse<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        return ApiResponse.success(stock);
    }

    @PutMapping("/{id}")
    public ApiResponse<Stock> updateStock(
            @PathVariable Long id,
            @RequestBody StockRequest req) {
        Stock updatedStock = stockService.updateStock(id, req);
        return ApiResponse.success(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ApiResponse.success(null);
    }
}
