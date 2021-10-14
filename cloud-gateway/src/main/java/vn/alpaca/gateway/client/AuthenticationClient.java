package vn.alpaca.gateway.client;;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.alpaca.response.wrapper.SuccessResponse;

@Headers({
        "Accept: application/json; charset=utf-8",
        "Content-Type: application/json" })
@FeignClient(value = "authentication-service")
public interface AuthenticationClient {

    @GetMapping("/verify-token")
    SuccessResponse<Integer> verifyToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}