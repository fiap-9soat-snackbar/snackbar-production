package com.snackbar.cooking.infrastructure.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.snackbar.cooking.client")
public class FeignConfig {
}