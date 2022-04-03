package com.mjanvier.ariadnexttp.config.webclient;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "idcheck.client")
public class IdCheckProperties {

    @NotBlank
    private String basePath;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
