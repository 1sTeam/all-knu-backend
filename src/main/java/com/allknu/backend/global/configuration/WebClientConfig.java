package com.allknu.backend.global.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${external-client.fcm}")
    private String fcmUrl;

    @Bean(name = "fcmClient")
    public WebClient fcmClient() {
        return WebClient.create(fcmUrl);
    }
}
