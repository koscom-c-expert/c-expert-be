package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.CreateStockRequest;
import com.koscom.cexpert.dto.UpdateStockRequest;
import com.koscom.cexpert.exception.EntityNotFoundException;
import com.koscom.cexpert.model.TestStock;
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
    public TestStock createStock(CreateStockRequest req) {
        TestStock newTestStock = new TestStock(req.getName(), req.getCurrentPrice());
        return stockRepository.save(newTestStock);
    }

    @Override
    public List<TestStock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public TestStock getStockById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Override
    public TestStock updateStock(Long id, UpdateStockRequest req) {
        TestStock testStockToUpdate = stockRepository.findById(id).orElse(null);

        // 찾을 수 없는 경우
        if (testStockToUpdate == null) {
            throw new EntityNotFoundException();
        }

        // update
        testStockToUpdate.setName(req.getName());
        testStockToUpdate.setCurrentPrice(req.getCurrentPrice());
        return stockRepository.save(testStockToUpdate);
    }

    @Override
    public void deleteStock(Long id) {
        TestStock testStockToUpdate = stockRepository.findById(id).orElse(null);

        // 찾을 수 없는 경우
        if (testStockToUpdate == null) {
            throw new EntityNotFoundException();
        }

        // delete
        stockRepository.deleteById(id);
    }
}
