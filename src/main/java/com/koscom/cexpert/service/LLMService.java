package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.ClassificationRequest;
import com.koscom.cexpert.dto.ClassificationResponse;

import java.util.List;

public interface LLMService {
    List<ClassificationResponse> classifyStocks(ClassificationRequest req);
}
