package com.koscom.cexpert.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CreateClassificationResponse {
    private final List<StockCategory> stockCategories;
    private final List<StockDto> stockDtos;
}
