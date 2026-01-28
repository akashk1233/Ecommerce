package com.ecom.orderservice.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {
    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
