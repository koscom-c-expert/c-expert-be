package com.koscom.cexpert.service;

import com.koscom.cexpert.model.TestStock;

import java.util.List;
import java.util.Map;

public interface LLMService {
    Map<String, List<TestStock>> classifyStocksByKeyword(List<TestStock> testStocks, String keyword);
}
