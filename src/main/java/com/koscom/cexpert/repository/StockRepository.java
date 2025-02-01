package com.koscom.cexpert.repository;

import com.koscom.cexpert.model.TestStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<TestStock, Long> {
}
