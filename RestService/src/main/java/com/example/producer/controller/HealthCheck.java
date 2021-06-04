package com.example.producer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheck {

    @RequestMapping("/health")
    public String getHealthCheck(){
        return "ALL OK";
    }

}
