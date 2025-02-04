package com.koscom.cexpert.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RebalanceRequest {
    private String category;
    private double percentage;
}
