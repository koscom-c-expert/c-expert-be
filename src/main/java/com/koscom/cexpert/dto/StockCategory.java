package com.koscom.cexpert.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StockCategory {
    private final Integer id;
    private final String name;
}
