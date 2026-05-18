package com.sentinelx.gateway.filter;

import com.sentinelx.gateway.security.JwtService;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter
        implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {

        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();

        System.out.println("Request Path: " + path);

        if (path.contains("/api/auth")) {

            return chain.filter(exchange);
        }

        String authHeader =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

        System.out.println("Authorization Header: " + authHeader);

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            System.out.println("Missing Token");

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        System.out.println("Extracted Token: " + token);

        if (!jwtService.isTokenValid(token)) {

            System.out.println("Invalid Token");

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        System.out.println("Token Valid");

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {

        return -1;
    }
}