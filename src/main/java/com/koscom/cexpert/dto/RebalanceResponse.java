package com.koscom.cexpert.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RebalanceResponse {
    private String category;
    private Integer level;
}
