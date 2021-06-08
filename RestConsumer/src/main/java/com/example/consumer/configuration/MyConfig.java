package com.example.consumer.configuration;

import com.example.consumer.controller.SampleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "consul-example")
public class MyConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyConfig.class);

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        LOGGER.info("Setting username to {}",username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        LOGGER.info("Setting password to {}",password);
        this.password = password;
    }

}
