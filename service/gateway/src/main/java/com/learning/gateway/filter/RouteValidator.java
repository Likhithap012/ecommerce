package com.learning.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

//@Component
//public class RouteValidator {
//
//    // Publicly accessible paths
//    private static final List<String> openEndpoints = List.of(
//            "/api/v1/auth/register",
//            "/api/v1/auth/login",
//            "/api/v1/auth/login/password",
//            "/api/v1/auth/login/otp",
//            "/api/v1/auth/verify",
//            "/api/v1/auth/validate",
//            "/api/v1/auth/customers"
//    );
//
////     Role-based access control
//    public static final Map<String, List<String>> roleAccessMap = new HashMap<>() {{
//        put("/api/v1/customers", List.of("ADMIN", "USER"));
//        put("/api/v1/products", List.of("ADMIN", "USER"));
//        put("/api/v1/categories", List.of("ADMIN"));
//        put("/api/v1/cart", List.of("USER"));
//        put("/api/v1/orders", List.of("USER"));
//        put("/api/v1/payments", List.of("USER"));
//    }};
//
//    // Checks if request path is secured
//    public Predicate<ServerHttpRequest> isSecured = request -> {
//        String path = request.getURI().getPath();
//        return openEndpoints.stream().noneMatch(path::startsWith);
//    };
//
////     Extract allowed roles for a given path
//    public List<String> getAllowedRolesForPath(String path) {
//        return roleAccessMap.entrySet().stream()
//                .filter(entry -> path.startsWith(entry.getKey()))
//                .map(Map.Entry::getValue)
//                .findFirst()
//                .orElse(Collections.emptyList());
//    }
//    public boolean requiresAuthorization(String path) {
//        return roleAccessMap.keySet().stream().anyMatch(path::startsWith);
//    }
//
//
//}

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

    public static final List<String> openEndpoints = List.of(
            "/api/v1/auth/",
            "/api/v1/auth/login",
            "/api/v1/auth/login/password",
            "/api/v1/auth/login/otp",
            "/api/v1/auth/register/",
            "/api/v1/customers"
    );

    public Predicate<String> isSecured =
            path -> openEndpoints.stream().noneMatch(path::equals);


}
