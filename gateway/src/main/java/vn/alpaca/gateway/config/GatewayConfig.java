package vn.alpaca.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route.AsyncBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.alpaca.gateway.interceptor.JwtAuthenticationFilter;

import java.util.function.Function;

//@Configuration
public class GatewayConfig  {

    @Autowired
    private JwtAuthenticationFilter filter;


}
