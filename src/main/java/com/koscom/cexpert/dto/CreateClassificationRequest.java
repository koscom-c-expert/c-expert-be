package com.koscom.cexpert.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CreateClassificationRequest {
    private List<String> stockCategories;
    private List<String> stocks;
}
