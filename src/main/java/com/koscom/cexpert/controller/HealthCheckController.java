package com.koscom.cexpert.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthCheckController {

    @GetMapping("/health")
    public @ResponseBody String healthCheck() {
        return "{\"status\": \"UP\"}";
    }
}
