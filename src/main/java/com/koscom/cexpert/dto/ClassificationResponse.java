package com.koscom.cexpert.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassificationResponse {
    private String ticker;

    private int averagePurchasePrice;

    private int quantity;

    private String newCategory;
}
