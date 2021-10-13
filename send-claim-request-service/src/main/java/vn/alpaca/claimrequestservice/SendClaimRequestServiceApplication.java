package vn.alpaca.claimrequestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SendClaimRequestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SendClaimRequestServiceApplication.class, args);
    }

}
