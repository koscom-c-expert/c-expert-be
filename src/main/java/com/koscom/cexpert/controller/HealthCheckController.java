package com.koscom.cexpert.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "{\"status\": \"UP\"}";
    }
}
