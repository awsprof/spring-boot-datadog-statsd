package com.example.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class ConsumingService {

    @Autowired
    @Qualifier(value = "remoteRestTemplate")
    private RestTemplate rest;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String springApplicationName;

    public String serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("rest-service");
        if (list != null && list.size() > 0) {

            String host = list.get(0).getHost();
            int port = list.get(0).getPort();
            String protocol = null;
            String sayHelloServiceUrl = null;
            if(list.get(0).getMetadata()!=null && !list.get(0).getMetadata().isEmpty() && list.get(0).getMetadata().containsKey("protocol")){
                protocol = list.get(0).getMetadata().get("protocol");
            }else{
                protocol ="http";
            }

            if(list.get(0).getMetadata()!=null && !list.get(0).getMetadata().isEmpty() && list.get(0).getMetadata().containsKey("sayHelloServiceUrl")){
                sayHelloServiceUrl = list.get(0).getMetadata().get("sayHelloServiceUrl");
            }else{
                sayHelloServiceUrl = "/service/sayHello/{user}";
            }

            return protocol + "://" + host + ":" + port + sayHelloServiceUrl;
        }
        return null;
    }

    public String getUser(String user) {
        String endpoint = serviceUrl();
        //String user = sampleEntity.get().getSurname();
        String response = rest.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
        }, user).getBody();
        return "Response Received as " + response + " -  " + new Date();
    }

    @Bean(name = "remoteRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
