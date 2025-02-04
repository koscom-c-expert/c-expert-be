package com.koscom.cexpert.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LLMStockDto {
    private String newCategory;
    private String reason;
}
