package com.koscom.cexpert.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ClassificationRequest {
    private String keyword;
    private String userId;
}
