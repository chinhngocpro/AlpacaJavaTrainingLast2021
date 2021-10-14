package vn.alpaca.handleclaimrequestservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.CustomerResponse;
import vn.alpaca.response.wrapper.SuccessResponse;

@FeignClient(value = "customer-service", path = "/customers")
public interface CustomerFeignClient {

    @GetMapping("/search/id-card/{idCardNumber}")
    SuccessResponse<CustomerResponse>
    getByIdCardNumber(@PathVariable String idCardNumber);
}
