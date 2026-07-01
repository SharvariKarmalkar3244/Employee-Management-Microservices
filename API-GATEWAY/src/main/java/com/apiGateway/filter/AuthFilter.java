package com.apiGateway.filter;

import com.apiGateway.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Validator validator;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("Inside AuthFilter");
            if (validator.predicate.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new BadRequestException("Authorization is missing", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                String token = null;
                if (null != authHeader && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(token);
                } catch (Exception e) {
                    throw new BadRequestException("Invalid token", HttpStatus.UNAUTHORIZED);
                }
                // Token validation will go here
            }

            return chain.filter(exchange);
        };
    }

    public static class Config{

    }

}
