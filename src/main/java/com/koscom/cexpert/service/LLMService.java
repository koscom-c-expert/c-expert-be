package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.Stock;
import com.koscom.cexpert.dto.StockCategory;
import com.koscom.cexpert.model.TestStock;

import java.util.List;
import java.util.Map;

public interface LLMService {
    List<Stock> classifyStocks(List<StockCategory> stockCategories, List<String> stocks);
}
