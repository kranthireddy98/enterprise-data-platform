package com.enterprise.platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/db")
@RequiredArgsConstructor
public class DbTestController {

    private final DataSource dataSource;

    @GetMapping("/ping")
    public String ping() throws Exception {
        try (var con = dataSource.getConnection()) {
            return "DB OK";
        }
    }
}
