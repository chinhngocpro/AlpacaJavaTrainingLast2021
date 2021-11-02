package vn.alpaca.handleclaimrequestservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.alpaca.common.dto.response.CustomerResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;

@FeignClient(value = "customer-service")
public interface CustomerFeignClient {

  @GetMapping("/customers/_search/id-card/{idCardNumber}")
  SuccessResponse<CustomerResponse> getByIdCardNumber(@PathVariable String idCardNumber);
}
