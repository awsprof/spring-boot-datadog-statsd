package com.example.consumer.controller;

import com.example.consumer.service.ConsumingService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class SampleController {

    private final static Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    private final MeterRegistry registry;

    private Timer timer;

    @Value("${spring.application.name}")
    private String springApplicationName;

    public SampleController(MeterRegistry registry) {
        this.registry = registry;
        timer = registry.timer(springApplicationName+"_restConsumerTimer","team","springrocks","environment","dev");
    }

    @Autowired
    private ConsumingService consumingService;

    @Timed(percentiles = {0.5, 0.95, 0.999}, histogram = true)
    @GetMapping("/invoke/{user}")
    public String invokeUser(@PathVariable String user) {
        LOGGER.info("Incoming user is: {}",user);
        timer.record(()->{
            try{
                Thread.sleep((long) (Math.random()*500));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        });
        return consumingService.getUser(user);
    }
}
