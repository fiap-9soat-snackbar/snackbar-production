package com.snackbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
    "com.snackbar", 
    "com.snackbar.cooking",
    "com.snackbar.pickup",
})
@EnableFeignClients(basePackages = {"com.snackbar.cooking.client", "com.snackbar.pickup.client"})
public class SnackbarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnackbarApplication.class, args);
    }

}
