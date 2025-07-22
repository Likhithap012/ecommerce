package com.learning.gateway.filter;

import com.learning.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

//@Component
//@RequiredArgsConstructor
//public class AuthenticationFilter implements GlobalFilter, Ordered {
//
//    private final JwtUtil jwtUtil;
//    private final RouteValidator routeValidator;
//    private static final List<String> openEndpoints = List.of(
//            "/api/v1/auth/",
//            "/api/v1/auth/login/",
//            "/api/v1/auth/login/password",
//            "/api/v1/auth/login/otp",
//            "/api/v1/auth/verify",
//            "/api/v1/auth/validate"
//
//    );
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getURI().getPath();
//        for(String openEndpoint : openEndpoints) {
//            if (path.startsWith(openEndpoint)) {
//                // If the path matches an open endpoint, skip authentication
//                return chain.filter(exchange);
//            }
//        }
//        // If the path is not an open endpoint, proceed with authentication
//
//
//        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        System.out.println(authHeader);
//       if( authHeader == null || !authHeader.startsWith("Bearer ")) {
//           exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//       }
//
//
//
//        String token = authHeader.substring(7);
//
//        try {
//            Claims claims = jwtUtil.extractAllClaims(token);
//
//            // Check token expiration
//            if (jwtUtil.isTokenExpired(token)) {
//                return this.onError(exchange, "Token expired", HttpStatus.UNAUTHORIZED);
//            }
//
//            List<String> roles = (List<String>) claims.get("roles");
//            List<String> allowedRoles = routeValidator.getAllowedRolesForPath(path);
//
//            if (!allowedRoles.isEmpty() && roles.stream().noneMatch(allowedRoles::contains)) {
//                return this.onError(exchange, "Access denied: Insufficient role", HttpStatus.FORBIDDEN);
//            }
//
//            // Optionally: forward user info to downstream services
//            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
//                    .header("X-User-Id", claims.getSubject())
//                    .build();
//            return chain.filter(exchange.mutate().request(mutatedRequest).build());
//
//        } catch (Exception e) {
//            return this.onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange, String errMsg, HttpStatus status) {
//        exchange.getResponse().setStatusCode(status);
//        return exchange.getResponse().setComplete();
//    }
//
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//
//}


//
//
//
//
////import java.util.stream.Collectors;
////
////@Component
////@RequiredArgsConstructor
////public class AuthenticationFilter implements GlobalFilter, Ordered {
////
////    private final JwtUtil jwtUtil;
////    private final RouteValidator routeValidator;
////
////    private static final List<String> openEndpoints = List.of(
////            "/api/v1/auth/register",
////            "/api/v1/auth/login",
////            "/api/v1/auth/login/password",
////            "/api/v1/auth/login/otp",
////            "/api/v1/auth/verify",
////            "/api/v1/auth/validate"
////    );
////
////    @Override
////    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
////        ServerHttpRequest request = exchange.getRequest();
////        String path = request.getURI().getPath();
////
////        for (String openEndpoint : openEndpoints) {
////            if (path.startsWith(openEndpoint)) {
////                return chain.filter(exchange);
////            }
////        }
////
////        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
////        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
////            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
////            return exchange.getResponse().setComplete();
////        }
////
////        String token = authHeader.substring(7);
////        try {
////            Claims claims = jwtUtil.extractAllClaims(token);
////
////            if (jwtUtil.isTokenExpired(token)) {
////                return this.onError(exchange, "Token expired", HttpStatus.UNAUTHORIZED);
////            }
////
////            Object roleClaim = claims.get("role");
////            List<String> roles;
////
////            if (roleClaim instanceof List<?>) {
////                roles = ((List<?>) roleClaim).stream()
////                        .map(Object::toString)
////                        .collect(Collectors.toList());
////            } else if (roleClaim instanceof String) {
////                roles = List.of((String) roleClaim);
////            } else {
////                return this.onError(exchange, "Invalid roles format in token", HttpStatus.UNAUTHORIZED);
////            }
////
////            List<String> allowedRoles = routeValidator.getAllowedRolesForPath(path);
////
////            System.out.println("JWT Roles: " + roles);
////            System.out.println("Allowed Roles for " + path + ": " + allowedRoles);
////
////            if (!allowedRoles.isEmpty() && roles.stream().noneMatch(allowedRoles::contains)) {
////                return this.onError(exchange, "Access denied: Insufficient role", HttpStatus.FORBIDDEN);
////            }
////
////            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
////                    .header("X-User-Id", claims.getSubject())
////                    .build();
////
////            return chain.filter(exchange.mutate().request(mutatedRequest).build());
////
////        } catch (Exception e) {
////            return this.onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
////        }
////    }
////
////    private Mono<Void> onError(ServerWebExchange exchange, String errMsg, HttpStatus status) {
////        System.out.println("AUTH ERROR: " + errMsg);
////        exchange.getResponse().setStatusCode(status);
////        return exchange.getResponse().setComplete();
////    }
////
////    @Override
////    public int getOrder() {
////        return -1;
////    }
////}
//import com.learning.gateway.util.JwtUtil;
//import io.jsonwebtoken.Claims;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class AuthenticationFilter implements GlobalFilter, Ordered {
//
//    private final JwtUtil jwtUtil;
//
//    // Define your public (open) endpoints here
//    private static final List<String> openEndpoints = List.of(
//            "/api/v1/auth/register",
//            "/api/v1/auth/login",
//            "/api/v1/auth/login/password",
//            "/api/v1/auth/login/otp",
//            "/api/v1/auth/verify",
//            "/api/v1/auth/validate"
//    );
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getURI().getPath();
//
//        // Allow public endpoints
//        for (String openEndpoint : openEndpoints) {
//            if (path.startsWith(openEndpoint)) {
//                return chain.filter(exchange);
//            }
//        }
//
//        // Check for Authorization header
//        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return this.onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
//        }
//
//        // Extract and validate JWT
//        String token = authHeader.substring(7);
//        try {
//            Claims claims = jwtUtil.extractAllClaims(token);
//
//            if (jwtUtil.isTokenExpired(token)) {
//                return this.onError(exchange, "Token expired", HttpStatus.UNAUTHORIZED);
//            }
//
//            // Optionally: pass user data downstream
//            ServerHttpRequest mutatedRequest = request.mutate()
//                    .header("X-User-Id", claims.getSubject())
//                    .header("X-User-Role", claims.get("role", String.class))
//                    .build();
//
//            return chain.filter(exchange.mutate().request(mutatedRequest).build());
//
//        } catch (Exception e) {
//            return this.onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus status) {
//        System.out.println("[Gateway] ERROR: " + error);
//        exchange.getResponse().setStatusCode(status);
//        return exchange.getResponse().setComplete();
//    }
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;
    public AuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        System.out.println("AuthenticationFilter constructor called!");
    }

    private static final List<String> openEndpoints = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/api/v1/auth/login/password",
            "/api/v1/auth/login/otp",
            "/api/v1/auth/verify",
            "/api/v1/auth/validate"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(" AuthenticationFilter triggered!");
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        System.out.println("[Gateway] Incoming request path: " + path);

        // Allow public endpoints
        for (String openEndpoint : openEndpoints) {
            if (path.startsWith(openEndpoint)) {
                System.out.println("[Gateway] Public endpoint accessed, skipping auth.");
                return chain.filter(exchange);
            }
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        System.out.println("[Gateway] Auth Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("[Gateway]  Missing or malformed Authorization header");
            return this.onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        System.out.println("[Gateway] Extracted JWT Token: " + token);

        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            System.out.println("[Gateway]  JWT Claims extracted: " + claims);

            if (jwtUtil.isTokenExpired(token)) {
                System.out.println("[Gateway]  Token is expired.");
                return this.onError(exchange, "Token expired", HttpStatus.UNAUTHORIZED);
            }

            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-Id", claims.getSubject())
                    .header("X-User-Role", claims.get("role", String.class))
                    .build();

            System.out.println("[Gateway] Token is valid. Forwarding request.");
            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            System.out.println("[Gateway]  Exception while validating JWT: " + e.getMessage());
            e.printStackTrace();
            return this.onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus status) {
        System.out.println("[Gateway] ERROR: " + error);
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

