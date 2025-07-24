package com.learning.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import java.util.function.Predicate;
import java.util.stream.Stream;


@Component
public class RouteValidator {

    public Predicate<ServerHttpRequest> isSecured = request ->
            Stream.of(
                    "/api/v1/auth/",
                    "/api/v1/auth/login",
                    "/api/v1/auth/login/password",
                    "/api/v1/auth/login/otp",
                    "/api/v1/auth/register/",
                    "/api/v1/customers"
            ).noneMatch(uri -> request.getURI().getPath().contains(uri));
}
