package com.EnterprisePlatform.config.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "db")
public class DbProperties {

    @NotBlank
    private String host;

    @NotNull
    private Integer port;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String database;


    private Pool pool = new Pool();

    @Getter
    @Setter
    public static class Pool{

        @NotNull
        private Integer maxSize = 10;

        @NotNull
        private Integer minIdle = 2;
    }
}
