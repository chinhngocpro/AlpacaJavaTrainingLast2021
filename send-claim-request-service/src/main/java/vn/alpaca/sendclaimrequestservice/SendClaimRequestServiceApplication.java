package vn.alpaca.sendclaimrequestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"vn.alpaca", "web.security"})
@EnableFeignClients(basePackages = "vn.alpaca")
public class SendClaimRequestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SendClaimRequestServiceApplication.class, args);
    }

}
