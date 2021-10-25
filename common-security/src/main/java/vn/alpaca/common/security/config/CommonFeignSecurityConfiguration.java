package vn.alpaca.common.security.config;

import feign.RequestInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.alpaca.common.security.object.AuthUser;
import vn.alpaca.constant.CustomHttpHeader;

@Configuration
@Log4j2
public class CommonFeignSecurityConfiguration {

    @Value("${spring.application.name}")
    String serviceName;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            try {
                Object principal =
                        SecurityContextHolder.getContext().getAuthentication()
                                .getPrincipal();

                if (principal instanceof AuthUser) {
                    AuthUser user = (AuthUser) principal;
                    requestTemplate.header(CustomHttpHeader.X_AUTHORIZED_USER,
                            Integer.toString(user.getId()));
                    log.info("User access");
                } else {
                    requestTemplate.header(
                            CustomHttpHeader.X_AUTHORIZED_SERVICE, serviceName);
                    log.info("Service access");
                }
                log.info("No authorization");
            } catch (Exception e) {
                requestTemplate.header(CustomHttpHeader.X_AUTHORIZED_SERVICE,
                        serviceName);
            }
        };
    }
}
