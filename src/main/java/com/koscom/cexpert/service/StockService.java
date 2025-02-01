package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.CreateStockRequest;
import com.koscom.cexpert.dto.UpdateStockRequest;
import com.koscom.cexpert.model.Stock;

import java.util.List;

public interface StockService {
    Stock createStock(CreateStockRequest req);
    List<Stock> getAllStocks();
    Stock getStockById(Long id);
    Stock updateStock(Long id, UpdateStockRequest req);
    void deleteStock(Long id);
}
