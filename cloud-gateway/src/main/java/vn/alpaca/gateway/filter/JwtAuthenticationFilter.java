package vn.alpaca.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import vn.alpaca.gateway.service.AuthService;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory {

    @Autowired
    AuthService authService;

    private final String X_AUTHORIZED_USER = "X-Authorized-User";

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            Integer userId = authService.validateToken(
                    request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

            if (userId == null) {
                return null;
            }

            request = request.mutate()
                    .header(X_AUTHORIZED_USER, userId.toString()).build();

            return chain.filter(exchange.mutate().request(request).build());
        });
    }
}