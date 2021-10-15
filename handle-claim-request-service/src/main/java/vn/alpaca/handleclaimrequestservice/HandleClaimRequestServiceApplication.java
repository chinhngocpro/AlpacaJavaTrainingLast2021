package vn.alpaca.handleclaimrequestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"vn.alpaca", "config.web", "config.method"})
@EnableEurekaClient
@EnableFeignClients(basePackages = "vn.alpaca")
public class HandleClaimRequestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandleClaimRequestServiceApplication.class, args);
    }

}
