package com.koscom.cexpert.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@RequiredArgsConstructor
public class UpdateStockRequest {

    @Size(min = 1, max = 20)
    private String name;

    @Range(min = 0)
    private double currentPrice;
}
