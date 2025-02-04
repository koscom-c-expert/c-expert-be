package com.koscom.cexpert.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stock")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String ticker;

    private int averagePurchasePrice;

    private int quantity;

    public Stock(String userId, String ticker, int averagePurchasePrice, int quantity) {
        this.userId = userId;
        this.ticker = ticker;
        this.averagePurchasePrice = averagePurchasePrice;
        this.quantity = quantity;
    }
}
