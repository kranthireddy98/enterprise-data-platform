package com.EnterprisePlatform.config.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    //App fails to start if value is null
    //With @value("${env}" Runtime error if value is missing
    @NotBlank
    private String environment;

}
