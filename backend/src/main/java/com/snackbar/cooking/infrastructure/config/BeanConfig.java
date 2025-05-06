package com.snackbar.cooking.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.infrastructure.gateways.OrderClientGateway;

@Configuration
public class BeanConfig {

    @Bean
    @Primary
    public OrderGateway orderGateway(OrderClientGateway orderClientGateway) {
        return orderClientGateway;
    }
}