package com.example.restservice2.controller;

import com.example.restservice2.configuration.MyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    private MyConfig myConfig;

    @GetMapping("/getConfigData")
    public MyConfig getMyConfig(){
        return myConfig;
    }


}
