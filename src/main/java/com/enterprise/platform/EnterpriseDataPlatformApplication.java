package com.enterprise.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EnterpriseDataPlatformApplication {
    private static final Logger log = LoggerFactory.getLogger(EnterpriseDataPlatformApplication.class);

    public static void main(String[] args) {
        log.info("Logs r");
       SpringApplication.run(EnterpriseDataPlatformApplication.class,args);
    }
}