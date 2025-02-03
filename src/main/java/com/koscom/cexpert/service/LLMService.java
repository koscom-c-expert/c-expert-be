package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.ClassificationRequest;
import com.koscom.cexpert.dto.ClassificationResponse;
import com.koscom.cexpert.dto.RebalanceRequest;
import com.koscom.cexpert.dto.RebalanceResponse;

import java.util.List;

public interface LLMService {
    List<ClassificationResponse> classifyStocks(ClassificationRequest req);
    List<RebalanceResponse> rebalanceCategories(List<RebalanceRequest> req);
}
