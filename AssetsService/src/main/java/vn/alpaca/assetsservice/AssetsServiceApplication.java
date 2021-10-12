package vn.alpaca.assetsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AssetsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetsServiceApplication.class, args);
    }

}
