package vn.alpaca.customerservice.config;

import config.web.WebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class CustomerWebSecurityConfig extends WebSecurityConfig {

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
                .antMatchers("/search/id-card/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(userAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
