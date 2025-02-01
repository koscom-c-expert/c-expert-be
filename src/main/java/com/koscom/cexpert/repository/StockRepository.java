package com.koscom.cexpert.repository;

import com.koscom.cexpert.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
