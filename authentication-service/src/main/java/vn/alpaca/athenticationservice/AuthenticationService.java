package vn.alpaca.athenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"vn.alpaca"})
@EnableFeignClients(basePackages = "vn.alpaca")
public class AuthenticationService {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationService.class, args);
    }

}
