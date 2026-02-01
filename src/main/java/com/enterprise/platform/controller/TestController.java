package com.enterprise.platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger log =
            LoggerFactory.getLogger(TestController.class);

    @Operation(
            summary = "test Endpoint",
            description = "Test end point to return Ok"
    )
    @GetMapping
    public String test() {
        log.info("Test endpoint called");
        return "OK";
    }
}
