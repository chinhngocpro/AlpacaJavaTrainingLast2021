package vn.alpaca.contractservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"vn.alpaca", "config.method"})
@EnableEurekaClient
@EnableFeignClients(basePackages = "vn.alpaca")
public class ContractServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractServiceApplication.class, args);
    }

}
