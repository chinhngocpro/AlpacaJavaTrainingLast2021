package vn.alpaca.handleclaimrequestservice.config;

import config.web.WebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class HandleClaimWebSecurityConfig extends WebSecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers(
                        "/payments/customer/**",
                        "/claim-requests/customer/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(userAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
