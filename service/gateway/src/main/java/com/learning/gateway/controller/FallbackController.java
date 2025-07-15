package com.learning.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping(path = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<String> fallback(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Service Unavailable: " + path);
    }
}
