package com.manoj.gateway.filter;

import com.manoj.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (path.contains("/api/auth") || path.contains("/ws")) {
            return chain.filter(exchange);
        }
        String authHeader =
                exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        System.out.println("PATH: " + path);
        System.out.println("AUTH HEADER: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        System.out.println("TOKEN: " + token);

        if (!jwtUtil.isTokenValid(token)) {

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        Claims claims = jwtUtil.extractClaims(token);
        String username = claims.getSubject();

        ServerWebExchange modifiedExchange =
                exchange.mutate()
                        .request(
                                exchange.getRequest().mutate()
                                        .header("X-User-Username", username)
                                        .build()
                        )
                        .build();
        return chain.filter(modifiedExchange);
    }
}