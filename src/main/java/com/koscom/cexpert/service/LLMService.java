package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.StockDto;
import com.koscom.cexpert.dto.StockCategory;

import java.util.List;

public interface LLMService {
    List<StockDto> classifyStocks(List<StockCategory> stockCategories, List<String> stocks);
}
