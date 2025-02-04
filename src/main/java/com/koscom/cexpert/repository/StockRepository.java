package com.koscom.cexpert.repository;

import com.koscom.cexpert.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByUserId(String userId);
}
