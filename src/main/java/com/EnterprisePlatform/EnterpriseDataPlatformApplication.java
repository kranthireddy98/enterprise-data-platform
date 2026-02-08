package com.EnterprisePlatform;

import com.EnterprisePlatform.config.profile.AppProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EnterpriseDataPlatformApplication {
    private static final Logger log = LoggerFactory.getLogger(EnterpriseDataPlatformApplication.class);
    private final AppProperties props;

    public EnterpriseDataPlatformApplication(AppProperties props) {
        this.props = props;
    }
    public static void main(String[] args) {

       SpringApplication.run(EnterpriseDataPlatformApplication.class,args);
    }

    @PostConstruct
    public void logEnv() {

        log.info("Application started in {} environment", props.getEnvironment());
    }
}