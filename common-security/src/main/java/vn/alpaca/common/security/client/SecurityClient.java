package vn.alpaca.common.security.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.alpaca.common.security.object.AuthPermission;
import vn.alpaca.common.security.object.AuthUser;
import vn.alpaca.dto.wrapper.SuccessResponse;

import java.util.List;

@Headers({
        "Accept: application/json; charset=utf-8",
        "Content-Type: application/json"})
@FeignClient(value = "auth-server")
public interface SecurityClient {

    @GetMapping(path = "search/{username}")
    SuccessResponse<AuthUser> findByUserName(@PathVariable String username);

    @GetMapping(path = "/verify-user/{id}")
    SuccessResponse<AuthUser> findById(@PathVariable int id);

    @GetMapping(path = "/authorities")
    SuccessResponse<List<AuthPermission>> findAllPermissions();
}
