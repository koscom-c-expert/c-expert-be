package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.CreateStockRequest;
import com.koscom.cexpert.dto.UpdateStockRequest;
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
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public Stock createStock(CreateStockRequest req) {
        Stock newStock = new Stock(req.getName(), req.getCurrentPrice());
        return stockRepository.save(newStock);
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock updateStock(UpdateStockRequest req) {
        Stock stockToUpdate = stockRepository.findById(req.getId()).orElse(null);

        // 찾을 수 없는 경우
        if (stockToUpdate == null) {
            throw new EntityNotFoundException();
        }

        // update
        stockToUpdate.setName(req.getName());
        stockToUpdate.setCurrentPrice(req.getCurrentPrice());
        return stockRepository.save(stockToUpdate);
    }

    @Override
    public void deleteStock(Long id) {
        Stock stockToUpdate = stockRepository.findById(id).orElse(null);

        // 찾을 수 없는 경우
        if (stockToUpdate == null) {
            throw new EntityNotFoundException();
        }

        // delete
        stockRepository.deleteById(id);
    }
}
