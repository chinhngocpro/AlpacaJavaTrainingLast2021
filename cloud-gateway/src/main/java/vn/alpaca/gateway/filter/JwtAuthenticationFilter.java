package vn.alpaca.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.SetPathGatewayFilterFactory;
import org.springframework.cloud.gateway.support.HasRouteId;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.alpaca.constant.CustomHttpHeader;
import vn.alpaca.gateway.service.AuthService;
import vn.alpaca.response.wrapper.ErrorResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper mapper;

    public JwtAuthenticationFilter() {
        super(JwtAuthenticationFilter.Config.class);
    }


    @Override
    public GatewayFilter apply(JwtAuthenticationFilter.Config config) {

        return ((exchange, chain) -> {
            if (config != null && config.getIgnoredPaths() != null && !config.getIgnoredPaths().isEmpty()) {
                AntPathMatcher matcher = new AntPathMatcher();

                for (String pattern : config.getIgnoredPaths()) {
                    if (matcher.match(pattern, exchange.getRequest().getPath().toString())) {
                        return chain.filter(exchange);
                    }
                }
            }

            try {
                ServerHttpRequest request = exchange.getRequest();

                Integer userId = authService.validateToken(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

                if (userId == null) {
                    ErrorResponse errorResponse = new ErrorResponse(401, "Invalid token");
                    return writeErrorResponse(errorResponse,exchange.getResponse(), HttpStatus.UNAUTHORIZED);
                }

                request = request.mutate().header(CustomHttpHeader.X_AUTHORIZED_USER, userId.toString()).build();

                return chain.filter(exchange.mutate().request(request).build());
            } catch (FeignException.Forbidden | FeignException.Unauthorized e) {
                ErrorResponse errorResponse = new ErrorResponse(401, "Invalid token");
                return writeErrorResponse(errorResponse,exchange.getResponse(), HttpStatus.UNAUTHORIZED);
            } catch (Exception e) {
                ErrorResponse errorResponse = new ErrorResponse(500, "Server Error");
                return writeErrorResponse(errorResponse,exchange.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    protected Mono<Void> writeErrorResponse(ErrorResponse errorResponse, ServerHttpResponse response, HttpStatus status) {
        String text = "";
        try {
            text = mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
        }

        response.setStatusCode(status);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Flux.just(buffer));
    }

    @Getter @Setter
    public static class Config {
        List<String> ignoredPaths;
    }
}