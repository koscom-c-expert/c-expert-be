package com.koscom.cexpert.service;

import com.koscom.cexpert.model.Stock;

import java.util.List;
import java.util.Map;

public interface LLMService {
    Map<String, List<Stock>> classifyStocksByKeyword(List<Stock> stocks, String keyword);
}
