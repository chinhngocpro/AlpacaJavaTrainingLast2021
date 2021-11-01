package vn.alpaca.security.config;

import feign.RequestInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.alpaca.common.constant.CustomHttpHeader;
import vn.alpaca.security.model.AuthUser;

@Configuration
@Log4j2
public class CommonFeignConfig {

    @Value("${spring.application.name}")
    String serviceName;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            try {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof AuthUser) {
                    AuthUser user = (AuthUser) principal;
                    requestTemplate.header(CustomHttpHeader.X_AUTHORIZED_USER,
                                           Integer.toString(user.getId()));
                } else {
                    requestTemplate.header(CustomHttpHeader.X_AUTHORIZED_SERVICE, serviceName);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
                requestTemplate.header(CustomHttpHeader.X_AUTHORIZED_SERVICE, serviceName);
            }
        };
    }
}
