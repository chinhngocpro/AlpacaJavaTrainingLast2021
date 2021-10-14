package vn.alpaca.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.alpaca.constant.CustomHttpHeader;
import vn.alpaca.gateway.service.AuthService;
import vn.alpaca.response.wrapper.ErrorResponse;

import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory {

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper mapper;

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            Integer userId = authService.validateToken(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

            if (userId == null) {
                ErrorResponse errorResponse = new ErrorResponse(401, "Invalid token");
                return writeErrorResponse(errorResponse,exchange.getResponse());
            }

            request = request.mutate().header(CustomHttpHeader.X_AUTHORIZED_USER, userId.toString()).build();

            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    protected Mono<Void> writeErrorResponse(ErrorResponse errorResponse, ServerHttpResponse response) {
        String text = "";
        try {
            text = mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Flux.just(buffer));
    }
}