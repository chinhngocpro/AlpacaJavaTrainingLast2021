package vn.alpaca.security.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.alpaca.common.dto.response.AuthorityResponse;
import vn.alpaca.common.dto.response.UserResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.security.model.AuthUser;

import java.util.List;

@Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
@FeignClient(value = "user-service")
public interface SecurityClient {

    @GetMapping(path = "/users/_auth/{username}")
    SuccessResponse<AuthUser> findByUserName(@PathVariable String username);

    @GetMapping(path = "/users/{id}")
    SuccessResponse<AuthUser> findById(@PathVariable int id);

    @GetMapping(path = "/authorities")
    SuccessResponse<List<AuthorityResponse>> getAuthorities();
}
