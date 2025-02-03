package com.koscom.cexpert.dto;

import com.koscom.cexpert.model.Stock;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@RequiredArgsConstructor
public class StockRequest {
    private String userId;

    @Size(min = 1, max = 20)
    private String ticker;

    @Range(min = 0)
    private int averagePurchasePrice;

    @Range(min = 0)
    private int quantity;

    public Stock toStock() {
        return new Stock(userId, ticker, averagePurchasePrice, quantity);
    }
}
