package com.mjanvier.ariadnexttp.config.webclient;

import com.mjanvier.ariadnexttp.idcheck.api.client.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Configuration
public class IdCheckWebClientConfig {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, IdCheckProperties idCheckProperties) {
        return restTemplateBuilder
                .basicAuthentication(idCheckProperties.getUsername(), idCheckProperties.getPassword())
                .build();
    }

    @Bean
    @Primary
    ApiClient idCheckApiClient(ApiClient apiClient, IdCheckProperties idCheckProperties) {
        return apiClient
                .setBasePath(idCheckProperties.getBasePath());
    }
}
