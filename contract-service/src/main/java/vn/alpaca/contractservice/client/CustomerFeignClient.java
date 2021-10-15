package vn.alpaca.contractservice.client;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.alpaca.contractservice.object.wrapper.response.CustomerResponse;
import vn.alpaca.response.exception.StashErrorDecoder;
import vn.alpaca.response.wrapper.SuccessResponse;

@FeignClient(value = "customer-service")
public interface CustomerFeignClient {

    @GetMapping("/search/id-card/{idCardNumber}")
    SuccessResponse<CustomerResponse>
    getByIdCardNumber(@PathVariable String idCardNumber);

}

@LoadBalancerClient(value = "customer-service")
class CustomerServiceLoadBalanceConfig {
    @LoadBalanced
    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder().errorDecoder(new StashErrorDecoder());
    }
}
