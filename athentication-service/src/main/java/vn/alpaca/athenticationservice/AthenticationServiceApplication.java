package vn.alpaca.athenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AthenticationServiceApplication.class, args);
    }

}
