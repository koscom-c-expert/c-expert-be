package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.StockRequest;
import com.koscom.cexpert.exception.EntityNotFoundException;
import com.koscom.cexpert.model.Stock;
import com.koscom.cexpert.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    public Stock createStock(StockRequest req) {
        Stock newStock = req.toStock();
        return stockRepository.save(newStock);
    }

    public List<Stock> getAllStocksByUserId(String userId) {
        return stockRepository.findAllByUserId(userId);
    }

    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    public Stock updateStock(Long id, StockRequest req) {
        Stock stockToUpdate = stockRepository.findById(id).orElse(null);

        if (stockToUpdate == null) {
            throw new EntityNotFoundException();
        }

        stockToUpdate.setTicker(req.getTicker());
        stockToUpdate.setAveragePurchasePrice(req.getAveragePurchasePrice());
        stockToUpdate.setQuantity(req.getQuantity());
        return stockRepository.save(stockToUpdate);
    }

    public void deleteStock(Long id) {
        Stock stockToUpdate = stockRepository.findById(id).orElse(null);

        if (stockToUpdate == null) {
            throw new EntityNotFoundException();
        }

        stockRepository.deleteById(id);
    }
}
