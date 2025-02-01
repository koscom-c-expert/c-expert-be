package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.CreateStockRequest;
import com.koscom.cexpert.dto.UpdateStockRequest;
import com.koscom.cexpert.model.TestStock;

import java.util.List;

public interface StockService {
    TestStock createStock(CreateStockRequest req);
    List<TestStock> getAllStocks();
    TestStock getStockById(Long id);
    TestStock updateStock(Long id, UpdateStockRequest req);
    void deleteStock(Long id);
}
