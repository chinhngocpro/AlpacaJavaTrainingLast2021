package vn.alpaca.sendclaimrequestservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.sendclaimrequestservice.object.wrapper.response.CustomerResponse;

@FeignClient(value = "customer-service", path = "/customers")
public interface CustomerFeignClient {

    @GetMapping("/search/id-card/{idCardNumber}")
    SuccessResponse<CustomerResponse>
    getByIdCardNumber(@PathVariable String idCardNumber);

}
