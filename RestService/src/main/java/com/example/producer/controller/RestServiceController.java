package com.example.producer.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class RestServiceController {

    private final MeterRegistry registry;

    private Timer timer;

    @Value("${spring.application.name}")
    private String springApplicationName;

    public RestServiceController(MeterRegistry registry) {
        this.registry = registry;
        timer = registry.timer(springApplicationName+"_restServiceTimer","team","springrocks","environment","dev");
    }

    @Timed(percentiles = {0.5, 0.95, 0.999}, histogram = true)
    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name){
        timer.record(()->{
            try{
                Thread.sleep((long) (Math.random()*500));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        });
        return "Hello " + name;
    }

}
