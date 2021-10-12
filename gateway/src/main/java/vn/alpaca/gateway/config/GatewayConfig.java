package vn.alpaca.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.alpaca.gateway.interceptor.JwtAuthenticationFilter;

//@Configuration
public class GatewayConfig  {
    @Autowired
    private JwtAuthenticationFilter filter;

}
