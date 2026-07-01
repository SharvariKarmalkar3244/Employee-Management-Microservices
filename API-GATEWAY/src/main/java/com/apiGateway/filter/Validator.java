package com.apiGateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;

@Component
public class Validator {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static final List<String> endpoints = List.of(
            "/auth/register-user",
            "/auth/generate-token",
            "/auth/validate-token/**"
    );

//    public Predicate<ServerHttpRequest> predicate = request -> {
//        String requestPath = request.getURI().getPath();
//
//        return endpoints.stream()
//                .noneMatch(pattern -> antPathMatcher.match(pattern, requestPath));
//    };
    public Predicate<ServerHttpRequest> predicate = request -> {

        String path = request.getURI().getPath();
        boolean secured = endpoints.stream()
                .noneMatch(uri -> antPathMatcher.match(uri, path));

        System.out.println("Path = " + path);
        System.out.println("Secured = " + secured);

        return secured;
    };
}