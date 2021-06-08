package com.example.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsumingService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConsumingService.class);

    @Autowired
    @Qualifier(value = "remoteRestTemplate")
    private RestTemplate rest;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String springApplicationName;

    @Value("${restService.localProxyPort}")
    private String restServiceProxyPort;

    public String serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("rest-service");
        if (list != null && list.size() > 0) {

            /*String host = list.get(0).getHost();
            int port = list.get(0).getPort();*/
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
                sayHelloServiceUrl = "/service/sayHello/{name}";
            }

            LOGGER.info("Returning URL: {}",protocol + "://127.0.0.1:" + restServiceProxyPort + sayHelloServiceUrl);

            return protocol + "://127.0.0.1:" + restServiceProxyPort + sayHelloServiceUrl;
        }
        return null;
    }

    public String getUser(String user) {
        String endpoint = serviceUrl();
        //String user = sampleEntity.get().getSurname();
/*        String response = rest.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
        }, user).getBody();*/
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", user);

        String response = rest.getForObject(endpoint,String.class,params);

        LOGGER.info("Raw Response: {}",response);
        return "Response Received as " + response + " -  " + new Date();
    }

    @Bean(name = "remoteRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
