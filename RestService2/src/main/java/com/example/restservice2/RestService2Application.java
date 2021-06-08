package com.example.restservice2;

import com.example.restservice2.configuration.MyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = MyConfig.class)
public class RestService2Application {

    public static void main(String[] args) {
        SpringApplication.run(RestService2Application.class, args);
    }

}
